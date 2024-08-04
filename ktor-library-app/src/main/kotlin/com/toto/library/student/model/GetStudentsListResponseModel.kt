package com.toto.library.student.model

import org.bson.types.ObjectId

data class GetStudentsListResponseModel(
    val _id: String = ObjectId().toString(),
    val studentFullName: String,
    val studentPhoneNumber: String,
    val seatNumber: String,
    val studentFee: String,
    val studentAddress: String,
)
