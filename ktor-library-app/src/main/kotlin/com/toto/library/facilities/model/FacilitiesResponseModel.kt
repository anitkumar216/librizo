package com.toto.library.facilities.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class FacilitiesResponseModel(
    @BsonId
    val _id: String = ObjectId().toString(),
    val name: String
)
