package com.toto.library.student.routes

import com.toto.library.common.model.GenericResponse
import com.toto.library.common.repository.StudentDetailsCollection
import com.toto.library.student.model.StudentDetailsPayloadModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.studentRoutes(
    studentCollection: StudentDetailsCollection
) {
    routing {
        authenticate {
            post("/addStudentDetails") {
                val requestBody = call.receive<StudentDetailsPayloadModel>()
                val principal = call.principal<JWTPrincipal>()
                val libraryId = principal!!.payload.getClaim("libraryId").asString()
                val studentDetailsPayloadModel = StudentDetailsPayloadModel(
                    studentFullName = requestBody.studentFullName,
                    studentPhoneNumber = requestBody.studentPhoneNumber,
                    studentEmailAddress = requestBody.studentEmailAddress,
                    fieldOfStudy = requestBody.fieldOfStudy,
                    idProofNumber = requestBody.idProofNumber,
                    seatNumber = requestBody.seatNumber,
                    studentFee = requestBody.studentFee,
                    highestQualification = requestBody.highestQualification,
                    studentAddress = requestBody.studentAddress,
                    studentDOB = requestBody.studentDOB,
                    dateOfJoining = requestBody.dateOfJoining,
                    shiftDetails = requestBody.shiftDetails,
                    libraryId = libraryId
                )
                val studentDetails = studentCollection.addStudentDetails(studentDetailsPayloadModel)
                call.respond(
                    status = HttpStatusCode.Created,
                    GenericResponse(isSuccess = true, data = studentDetails)
                )
            }

            get("/getStudentDetails/{studentId}") {
                val id = call.parameters["studentId"]
                val student = studentCollection.getStudentById(id!!)
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
            }
        }
    }
}