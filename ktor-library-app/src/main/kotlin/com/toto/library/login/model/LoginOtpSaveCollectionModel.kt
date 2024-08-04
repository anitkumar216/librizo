package com.toto.library.login.model

import java.util.Date

data class LoginOtpSaveCollectionModel (
    val phoneNumber: String,
    val otp: String,
    val expiryTime: Date
)