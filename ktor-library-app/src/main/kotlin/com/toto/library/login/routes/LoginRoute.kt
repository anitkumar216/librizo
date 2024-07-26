package com.toto.library.login.routes

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.pinpoint.AmazonPinpointClientBuilder
import com.amazonaws.services.pinpoint.model.Message
import com.amazonaws.services.pinpoint.model.MessageRequest
import com.amazonaws.services.pinpoint.model.SendMessagesRequest
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.toto.library.common.model.GenericResponse
import com.toto.library.login.model.LoginResponseModel
import com.toto.library.common.repository.LibraryDetailsCollection
import com.toto.library.common.repository.OtpCollection
import com.toto.library.library.model.LibraryDetailsDataPayloadModel
import com.toto.library.login.model.VerifyOtpPayloadModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.routeLogin(secret: String, issuer: String, audience: String) {
    val userCollection = LibraryDetailsCollection()
    val otpCollection = OtpCollection()
    routing {
        get("/login/{phoneNumber}") {
            val phoneNumber = call.parameters["phoneNumber"]
            otpCollection.createTTLIndex()
            val expiryTime = Calendar.getInstance()
            expiryTime.add(Calendar.MINUTE, 5)
            val otp = generateOTP()
            sendOTP(phoneNumber!!, otp)
            val flag = otpCollection.saveOTP(phoneNumber, otp, expiryTime.time)
            if (flag) {
                call.respond(
                    status = HttpStatusCode.OK,
                    GenericResponse(isSuccess = true, data = "Otp sent successfully")
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    GenericResponse(isSuccess = false, data = "Something Wrong")
                )
            }
        }

        post("/verifyOtp") {
            val requestBody = call.receive<VerifyOtpPayloadModel>()
            if (otpCollection.verifyOTP(requestBody.phoneNumber, requestBody.otp)) {
                val librarySearch = userCollection.getLibraryByPhoneNumber(requestBody.phoneNumber)
                val longExpireAfter1Hr = System.currentTimeMillis() + 36_00_000_0
                if (librarySearch.isEmpty() || librarySearch[0].libraryName.isNullOrEmpty()) {
                    val library = LibraryDetailsDataPayloadModel(
                        null,
                        null,
                        requestBody.phoneNumber,
                        null,
                        null, null, null,
                        null, null, null,
                        null, null, null,
                        null, null
                    )
                    val id = userCollection.addLibraryDetails(library)._id
                    //val libraryId = userCollection.getLibraryByPhoneNumber(requestBody.phoneNumber)
                    val generatedToken = JWT.create()
                        .withAudience(audience)
                        .withIssuer(issuer)
                        .withClaim("libraryId", id)
                        .withExpiresAt(Date(longExpireAfter1Hr))
                        .sign(Algorithm.HMAC512(secret))

                    val loginResponseModel = LoginResponseModel(false, generatedToken)
                    call.respond(
                        status = HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = loginResponseModel)
                    )
                } else {
                    val generatedToken = JWT.create()
                        .withAudience(audience)
                        .withIssuer(issuer)
                        .withClaim("libraryId", librarySearch[0]._id)
                        .withExpiresAt(Date(longExpireAfter1Hr))
                        .sign(Algorithm.HMAC512(secret))

                    val loginResponseModel = LoginResponseModel(true, generatedToken)
                    call.respond(
                        status = HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = loginResponseModel)
                    )
                }
            } else {
                call.respond(status = HttpStatusCode.Unauthorized, "Otp verification failed")
            }
        }
    }
}

fun generateOTP(): String {
    val otp = Random().nextInt(900000) + 100000
    return otp.toString()
}


fun sendOTP(phoneNumber: String, otp: String) {
    val awsAccessKeyId = "YOUR_AWS_ACCESS_KEY_ID"
    val awsSecretAccessKey = "YOUR_AWS_SECRET_ACCESS_KEY"
    val region = "YOUR_AWS_REGION"
    val applicationId = "YOUR_PINPOINT_APPLICATION_ID"
    val message = "Your OTP is: $otp"

    val credentials = BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey)
    val client = AmazonPinpointClientBuilder.standard()
        .withCredentials(AWSStaticCredentialsProvider(credentials))
        .withRegion(region)
        .build()

    val smsMessageRequest = MessageRequest()
        .withAddresses(mapOf(phoneNumber to null))
        .withMessageConfiguration(
            com.amazonaws.services.pinpoint.model.DirectMessageConfiguration()
                .withSMSMessage(
                    com.amazonaws.services.pinpoint.model.SMSMessage()
                        .withBody(message)
                        .withMessageType("PROMOTIONAL") // or TRANSACTIONAL
                )
        )

    val smsRequest = SendMessagesRequest()
        .withApplicationId(applicationId)
        .withMessageRequest(smsMessageRequest)

    client.sendMessages(smsRequest)
    //println("OTP sent successfully.")
}
