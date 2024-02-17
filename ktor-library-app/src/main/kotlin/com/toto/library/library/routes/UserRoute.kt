package com.toto.library.library.routes

import com.toto.library.common.repository.LibraryDetailsCollection
import com.toto.library.library.model.LibraryDetailsDataPayloadModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.userRoutes(
    userCollection: LibraryDetailsCollection
) {
    routing {
        authenticate {

            get("/getAllLibrary") {
                val userAll = userCollection.getAllLibrary()
                call.respond(HttpStatusCode.OK, userAll)
            }

            get("/getLibraryById/{id}") {
                val id = call.parameters["id"]
                val user = userCollection.getLibraryById(id!!)
                if (user.isNotEmpty()) {
                    call.respond(HttpStatusCode.Found, user[0])
                } else {
                    call.respond(status = HttpStatusCode.NotFound, "Student not found")
                }
            }

            get("/getProfile") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal!!.payload.getClaim("libraryId").asString()
                val library = userCollection.getLibraryById(id!!)
                if (library.isNotEmpty()) {
                    call.respond(HttpStatusCode.Found, library[0])
                } else {
                    call.respond(status = HttpStatusCode.NotFound, "Library not found")
                }
            }

            delete("/deleteLibraryById/{id}") {
                val id = call.parameters["id"]
                val isDeleted = userCollection.deleteLibraryById(id!!)
                if (isDeleted) {
                    call.respond(status = HttpStatusCode.Accepted, "Student deleted Successfully")
                } else {
                    call.respond(status = HttpStatusCode.NotFound, "Student not found")
                }
            }

            post("/updateLibraryById/{id}") {
                val id = call.parameters["id"]
                val requestBody = call.receive<LibraryDetailsDataPayloadModel>()
                val isUpdated = userCollection.updateLibraryById(id!!, requestBody)
                if (isUpdated) {
                    call.respond(HttpStatusCode.Created, "Student Updated")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Something Wrong")
                }
            }

            /*post("/changeUserIdPassword/{id}") {
                val id = call.parameters["id"]
                val requestBody = call.receive<ChangeUserPasswordPayloadModel>()
                val user = userCollection.getUserById(id!!)
                val newUser = UserDataModel(id, user[0].username, user[0].emailId, user[0].phoneNumber, requestBody.password)
                val isUpdated = userCollection.updateUserById(id, newUser)
                if (isUpdated) {
                    call.respond(HttpStatusCode.Created, "Password Changed")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Something Wrong")
                }
            }*/
        }
    }
}