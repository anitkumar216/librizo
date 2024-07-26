package com.toto.library.library.model

data class LibraryDashboardResponseModel(
    val totalSeats: Int,
    val totalShift: Int,
    val totalStudents: Int,
    val shiftWiseStudents: ArrayList<Int>,
    val todayFeeDateStudents: Int
)
