package com.toto.library.register.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class RegisterPayloadModel(
    val emailId: String?,
    val phoneNumber: String?,
    val username: String?,
    val password: String?
)
