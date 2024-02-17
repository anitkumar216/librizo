package com.toto.library.facilities.routes

import com.toto.library.common.repository.LibraryDetailsCollection
import com.toto.library.common.repository.LibraryFacilitiesCollection
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.facilitiesRoute(
    facilitiesCollection: LibraryFacilitiesCollection
) {
    routing {
        authenticate {
            get("/getLibraryFacilities") {
                val allFacilities = facilitiesCollection.getAllLibraryFacilities()
                call.respond(HttpStatusCode.OK, allFacilities)
            }
        }
    }
}