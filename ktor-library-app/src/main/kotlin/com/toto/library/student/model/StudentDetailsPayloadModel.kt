package com.toto.library.student.model

import com.toto.library.library.model.LibraryShiftDetailModel
import org.bson.types.ObjectId

data class StudentDetailsPayloadModel(
    val _id: String = ObjectId().toString(),
    val studentFullName: String,
    val studentPhoneNumber: String,
    val studentEmailAddress: String?,
    val fieldOfStudy: String,
    val idProofNumber: String,
    val seatNumber: String,
    val studentFee: String,
    val highestQualification: String?,
    val studentAddress: String,
    val studentDOB: String,
    val dateOfJoining: String,
    val shiftDetails: ArrayList<LibraryShiftDetailModel>,
    val libraryId: String?
)