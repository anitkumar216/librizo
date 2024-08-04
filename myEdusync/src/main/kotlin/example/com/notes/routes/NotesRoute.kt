package example.com.notes.routes

import example.com.common.model.GenericResponse
import example.com.common.repository.NotesCollection
import example.com.notes.model.NotesPayloadModel

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.notesRoutes(
    notesCollection: NotesCollection
) {
    routing {
        post("/addNotesDetails") {
            val requestBody = call.receive<NotesPayloadModel>()
            //val principal = call.principal<JWTPrincipal>()
            //val notesId = principal!!.payload.getClaim("libraryId").asString()
            val notesPayloadModel = NotesPayloadModel(
                title = requestBody.title,
                categories = requestBody.categories,
                body = requestBody.body,
                author = requestBody.author
            )
            val notesDetails = notesCollection.addNotesDetails(notesPayloadModel)
            call.respond(
                status = HttpStatusCode.Created,
                message = GenericResponse(isSuccess = true, data = notesDetails)
            )
        }
        /*authenticate {

            *//*get("/getStudentDetails/{studentId}") {
                val id = call.parameters["studentId"]
                val student = notesCollection.getStudentById(id!!)
                if (student.isNotEmpty()) {
                    call.respond(HttpStatusCode.Found, student[0])
                } else {
                    call.respond(status = HttpStatusCode.NotFound, "Student not found")
                }
            }

            get("/getStudentsList") {
                val page = call.parameters["page"]?.toIntOrNull() ?: 0
                val limit = call.parameters["limit"]?.toIntOrNull() ?: 10
                val sortBy = call.parameters["sortBy"] ?: "studentFullName" //"seatNumber"
                val principal = call.principal<JWTPrincipal>()
                val libraryId = principal!!.payload.getClaim("libraryId").asString()
                val students = studentCollection.getAllStudent(libraryId!!, page, limit, sortBy)
                if (students.isNotEmpty()) {
                    call.respond(status = HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = students))
                } else {
                    call.respond(status = HttpStatusCode.NotFound,
                        GenericResponse(isSuccess = false, data = "Zero student found"))
                }
            }*//*
        }*/
    }
}