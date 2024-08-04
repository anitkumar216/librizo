package example.com.common.plugins

import example.com.common.repository.NotesCollection
import example.com.notes.routes.notesRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/*fun Application.configureLoginRouting(secret: String, issuer: String, audience: String) {
    routeLogin(secret = secret, issuer = issuer, audience = audience)
}*/

fun Application.configureNotesRouting(notesCollection: NotesCollection) {
    notesRoutes(notesCollection)
}