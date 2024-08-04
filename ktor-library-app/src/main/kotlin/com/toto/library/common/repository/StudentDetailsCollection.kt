package com.toto.library.common.repository

import com.mongodb.client.model.Indexes.ascending
import com.mongodb.client.model.Indexes.descending
import com.mongodb.client.model.Sorts
import com.toto.library.library.model.LibraryShiftDetailModel
import com.toto.library.student.model.StudentDetailsPayloadModel
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq

class StudentDetailsCollection {
    private val databaseFactory = DatabaseFactory()
    private val studentCollection: CoroutineCollection<StudentDetailsPayloadModel> =
        databaseFactory.database.getCollection("StudentDetailsCollection")

    suspend fun addStudentDetails(studentDetails: StudentDetailsPayloadModel): StudentDetailsPayloadModel {
        studentCollection.insertOne(studentDetails)
        return studentDetails
    }

    suspend fun getAllStudent(
        libraryId: String,
        page: Int,
        limit: Int,
        sortBy: String
    ): List<StudentDetailsPayloadModel> {
        val skip = page * limit
        val sortDirection = if (sortBy.startsWith("-")) -1 else 1
        val sortOptions = when (sortBy) {
            "seatNumber" -> if (sortDirection == -1) descending("seatNumber") else ascending("seatNumber")
            "studentFullName" -> if (sortDirection == -1) descending("studentFullName") else ascending("studentFullName")
            "dateOfJoining" -> if (sortDirection == -1) descending("dateOfJoining") else ascending("dateOfJoining")
            else -> error("Invalid sort field")
        }

        return studentCollection.find(StudentDetailsPayloadModel::libraryId eq libraryId).skip(skip).limit(limit)
            .sort(sortOptions).toList()
    }

    suspend fun getAllDueStudentStudent(
        libraryId: String,
        date: String
    ): List<StudentDetailsPayloadModel> {
        val students = studentCollection.find(
            StudentDetailsPayloadModel::libraryId eq libraryId
        ).toList()

        val filteredStudents = students.filter {
            it.dateOfJoining == date
        }
        return filteredStudents
    }

    suspend fun getAllStudent(libraryId: String): List<StudentDetailsPayloadModel> {
        return studentCollection.find(StudentDetailsPayloadModel::libraryId eq libraryId).toList()
    }

    suspend fun getAllStudent(libraryId: String, shiftName: String): List<StudentDetailsPayloadModel> {
        val students = studentCollection.find(
            StudentDetailsPayloadModel::libraryId eq libraryId
        ).toList()

        // Filter students based on matching shiftId
        val filteredStudents = students.filter { student ->
            student.shiftDetails.any { it.shiftName == shiftName }
        }

        // Sort filtered students by shiftId within shiftDetails
        return filteredStudents.sortedBy { student ->
            student.shiftDetails.find { it.shiftName == shiftName }?.shiftName
        }
    }

    suspend fun getStudentById(studentId: String): List<StudentDetailsPayloadModel> {
        return studentCollection.find(StudentDetailsPayloadModel::_id eq studentId).toList()
    }
}