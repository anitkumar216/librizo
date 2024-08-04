package com.toto.library.login.model

data class VerifyOtpPayloadModel(
    val phoneNumber: String,
    val otp: String
)
