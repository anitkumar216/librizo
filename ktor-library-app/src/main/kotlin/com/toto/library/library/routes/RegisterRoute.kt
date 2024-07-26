package com.toto.library.library.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.toto.library.common.model.GenericResponse
import com.toto.library.register.model.RegisterResponseModel
import com.toto.library.common.repository.LibraryDetailsCollection
import com.toto.library.library.model.LibraryDetailsDataPayloadModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.routeRegister(
    userCollection: LibraryDetailsCollection
) {
    routing {
        authenticate {
            post("/updateLibraryDetails") {
                val requestBody = call.receive<LibraryDetailsDataPayloadModel>()
                val principal = call.principal<JWTPrincipal>()
                val id = principal!!.payload.getClaim("libraryId").asString()
                val previousData = userCollection.getLibraryById(id!!)
                if (previousData.isNotEmpty()) {
                    val library = LibraryDetailsDataPayloadModel(
                        previousData[0]._id,
                        requestBody.emailId,
                        previousData[0].phoneNumber,
                        requestBody.libraryName,
                        requestBody.libraryAddress,
                        requestBody.totalSeat,
                        requestBody.registrationFee,
                        requestBody.securityDeposit,
                        requestBody.shiftDetails,
                        requestBody.openingTiming,
                        requestBody.closingTiming,
                        requestBody.libraryType,
                        requestBody.libraryFacilities,
                        requestBody.ownerAadharNumber,
                        requestBody.libraryLocation
                    )
                    userCollection.updateLibraryById(id, library)
                    call.respond(
                        status = HttpStatusCode.Created,
                        GenericResponse(isSuccess = true, data = id)
                    )
                }
            }
        }
    }
}