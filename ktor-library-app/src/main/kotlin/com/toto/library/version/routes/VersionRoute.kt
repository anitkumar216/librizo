package com.toto.library.version.routes

import com.toto.library.AppUtils.VersionConverter
import com.toto.library.common.model.GenericResponse
import com.toto.library.common.repository.CheckVersionCollection
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.versionRoute(
    checkVersionCollection: CheckVersionCollection
) {
    routing {
        get("/sales") {
            //val version = VersionConverter.versionToInt(call.parameters["version"]!!)
            val versionResponse = checkVersionCollection.getSalesData()
            call.respond(HttpStatusCode.OK, versionResponse)
        }
    }
}