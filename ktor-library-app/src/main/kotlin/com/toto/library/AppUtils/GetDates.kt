package com.toto.library.AppUtils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object GetDates {
    fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val currentDate = LocalDate.now()

        return currentDate.format(formatter)
    }

    fun isOneDayAfter(date1: String, date2: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val localDate1 = LocalDate.parse(date1, formatter)
        val localDate2 = LocalDate.parse(date2, formatter)

        return localDate2 == localDate1.plusDays(1)
    }

    fun compareDates(date1: String, date2: String): Int {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val localDate1 = LocalDate.parse(date1, formatter)
        val localDate2 = LocalDate.parse(date2, formatter)

        return localDate1.compareTo(localDate2)
    }
}