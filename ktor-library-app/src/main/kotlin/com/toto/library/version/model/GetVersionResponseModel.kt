package com.toto.library.version.model

data class GetVersionResponseModel(
    val _id: String,
    val iOSVersion: Int,
    val webVersion: Int,
    val androidVersion: Int,
)
