package example.com

import com.typesafe.config.ConfigFactory
import example.com.common.plugins.*
import example.com.common.repository.NotesCollection
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

fun main() {
    //EngineMain.main(args)
    val config = HoconApplicationConfig(ConfigFactory.load())
    val secret = config.property("jwt.secret").getString()
    val issuer = config.property("jwt.issuer").getString()
    val audience = config.property("jwt.audience").getString()
    val realm = config.property("jwt.realm").getString()
    val notesCollection = NotesCollection()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSerialization()
        configureSecurity(secret = secret, issuer = issuer, audience = audience, mRealm = realm)
        configureNotesRouting(notesCollection = notesCollection)
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
