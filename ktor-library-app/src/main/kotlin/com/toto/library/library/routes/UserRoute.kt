package com.toto.library.library.routes

import com.toto.library.AppUtils.GetDates
import com.toto.library.common.model.GenericResponse
import com.toto.library.common.repository.LibraryDetailsCollection
import com.toto.library.common.repository.StudentDetailsCollection
import com.toto.library.library.model.LibraryDashboardResponseModel
import com.toto.library.library.model.LibraryDetailsDataPayloadModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*

fun Application.userRoutes(
    userCollection: LibraryDetailsCollection,
    studentCollection: StudentDetailsCollection
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

            get("/getLibraryDashboardDetails") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal!!.payload.getClaim("libraryId").asString()
                val library = userCollection.getLibraryById(id!!)
                val students = studentCollection.getAllStudent(id)
                val totalSeats = library[0].totalSeat?.toInt()
                val shiftInfo = library[0].shiftDetails
                val totalShift = shiftInfo?.size
                val totalStudents = students.size
                val shiftWiseStudents = ArrayList<Int>()
                for (i in 0..< totalShift!!) {
                    shiftWiseStudents.add(studentCollection.getAllStudent(id, library[0].shiftDetails!![i].shiftName).size)
                }
                val date = GetDates.getCurrentDate()
                val todayFeeDateStudents = studentCollection.getAllDueStudentStudent(id, date)
                val totalTodayFeeDateStudents = todayFeeDateStudents.size
                val libraryDashboardResponseModel = LibraryDashboardResponseModel(
                    totalSeats!!, totalShift, totalStudents, shiftWiseStudents, totalTodayFeeDateStudents
                )
                call.respond(HttpStatusCode.OK, GenericResponse(true, libraryDashboardResponseModel))
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
        }
    }
}