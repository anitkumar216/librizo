package com.toto.library.common.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSecurity(secret: String, issuer: String, audience: String, mRealm: String) {
    authentication {
        jwt {
            realm = mRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC512(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )
            validate { credential ->
                val libraryId = credential.payload.getClaim("libraryId").asString()
                if (libraryId.isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(status = HttpStatusCode.Unauthorized,
                    "Token is not valid or has expired")
            }
        }
    }
}