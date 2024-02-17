package com.toto.library.common.repository

import com.mongodb.client.model.IndexOptions
import com.toto.library.library.model.LibraryDetailsDataPayloadModel
import com.toto.library.login.model.LoginOtpSaveCollectionModel
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import java.util.*
import java.util.concurrent.TimeUnit

class OtpCollection {
    private val databaseFactory = DatabaseFactory()
    private val otpCollection : CoroutineCollection<LoginOtpSaveCollectionModel> = databaseFactory.database.getCollection("otpCollection")

    suspend fun createTTLIndex() {
        otpCollection.createIndex(
            "{expiryTime: 1}",
            IndexOptions().expireAfter(0, TimeUnit.SECONDS)
        )
    }

    suspend fun saveOTP(phoneNumber: String, otp: String, expiryTime: Date) {
        val otpEntry = LoginOtpSaveCollectionModel(phoneNumber, otp, expiryTime)
        otpCollection.insertOne(otpEntry)
    }



    suspend fun verifyOTP(phoneNumber: String, userEnteredOTP: String): Boolean {
        val query = and(
            LoginOtpSaveCollectionModel::phoneNumber eq phoneNumber,
            LoginOtpSaveCollectionModel::otp eq userEnteredOTP
        )
        val otpEntry = otpCollection.findOne(query)
        if (otpEntry != null) {
            if (otpEntry.expiryTime.after(Date())) {
                deleteOTP(userEnteredOTP) // Delete the OTP entry if it's valid and not expired
                return true
            }
        }
        return false
    }

    suspend fun deleteOTP(otp: String) {
        val query = LoginOtpSaveCollectionModel::otp eq otp
        otpCollection.deleteOne(query)
    }
}