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
            post("/addLibraryDetails") {
                val requestBody = call.receive<LibraryDetailsDataPayloadModel>()
                val principal = call.principal<JWTPrincipal>()
                val id = principal!!.payload.getClaim("libraryId").asString()
                userCollection.updateLibraryById(id, requestBody)
                call.respond(
                    status = HttpStatusCode.Created,
                    GenericResponse(isSuccess = true, data = id)
                )
            }
        }
    }
}