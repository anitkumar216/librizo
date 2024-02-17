package com.toto.library.common.plugins

import com.toto.library.login.routes.routeLogin
import com.toto.library.library.routes.routeRegister
import com.toto.library.common.repository.LibraryDetailsCollection
import com.toto.library.common.repository.LibraryFacilitiesCollection
import com.toto.library.facilities.routes.facilitiesRoute
import com.toto.library.library.routes.userRoutes
import io.ktor.server.application.*

fun Application.configureLoginRouting(secret: String, issuer: String, audience: String) {
    routeLogin(secret = secret, issuer = issuer, audience = audience)
}

fun Application.configureRegisterRouting(userCollection: LibraryDetailsCollection) {
    routeRegister(userCollection)
}

fun Application.configureUserRouting(userCollection: LibraryDetailsCollection) {
    userRoutes(userCollection)
}

fun Application.configureLibraryFacilitiesRouting(facilitiesCollection: LibraryFacilitiesCollection) {
    facilitiesRoute(facilitiesCollection)
}
