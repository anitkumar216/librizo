package com.toto.library.common.plugins

import com.toto.library.common.repository.CheckVersionCollection
import com.toto.library.login.routes.routeLogin
import com.toto.library.library.routes.routeRegister
import com.toto.library.common.repository.LibraryDetailsCollection
import com.toto.library.common.repository.LibraryFacilitiesCollection
import com.toto.library.common.repository.StudentDetailsCollection
import com.toto.library.facilities.routes.facilitiesRoute
import com.toto.library.library.routes.userRoutes
import com.toto.library.student.routes.studentRoutes
import com.toto.library.version.routes.versionRoute
import io.ktor.server.application.*

fun Application.configureLoginRouting(secret: String, issuer: String, audience: String) {
    routeLogin(secret = secret, issuer = issuer, audience = audience)
}

fun Application.configureRegisterRouting(userCollection: LibraryDetailsCollection) {
    routeRegister(userCollection)
}

fun Application.configureUserRouting(userCollection: LibraryDetailsCollection,
                                     studentCollection: StudentDetailsCollection) {
    userRoutes(userCollection, studentCollection)
}

fun Application.configureStudentRouting(studentCollection: StudentDetailsCollection) {
    studentRoutes(studentCollection)
}

fun Application.configureLibraryFacilitiesRouting(facilitiesCollection: LibraryFacilitiesCollection) {
    facilitiesRoute(facilitiesCollection)
}

fun Application.configureVersionRouting(checkVersionCollection: CheckVersionCollection) {
    versionRoute(checkVersionCollection)
}
