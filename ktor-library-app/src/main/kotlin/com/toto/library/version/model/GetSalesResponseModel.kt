package com.toto.library.version.model

data class GetSalesResponseModel(
    val _id: String,
    val item: String,
    val price: Int,
    val quantity: Int,
    val date: String
)