package com.toto.library

import com.toto.library.common.plugins.*
import com.toto.library.common.repository.CheckVersionCollection
import com.toto.library.common.repository.LibraryDetailsCollection
import com.toto.library.common.repository.LibraryFacilitiesCollection
import com.toto.library.common.repository.StudentDetailsCollection
import com.typesafe.config.ConfigFactory
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

fun main() {
    val config = HoconApplicationConfig(ConfigFactory.load())
    val secret = config.property("jwt.secret").getString()
    val issuer = config.property("jwt.issuer").getString()
    val audience = config.property("jwt.audience").getString()
    val realm = config.property("jwt.realm").getString()
    val checkVersionCollection = CheckVersionCollection()
    val userCollection = LibraryDetailsCollection()
    val facilitiesCollection = LibraryFacilitiesCollection()
    val studentDetailsCollection = StudentDetailsCollection()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSerialization()
        configureSecurity(secret = secret, issuer = issuer, audience = audience, mRealm = realm)
        configureLoginRouting(secret = secret, issuer = issuer, audience = audience)
        configureVersionRouting(checkVersionCollection = checkVersionCollection)
        configureRegisterRouting(userCollection = userCollection)
        configureUserRouting(userCollection = userCollection,
            studentCollection = studentDetailsCollection)
        configureStudentRouting(studentDetailsCollection)
        configureLibraryFacilitiesRouting(facilitiesCollection = facilitiesCollection)
    }.start(wait = true)
}

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
}
