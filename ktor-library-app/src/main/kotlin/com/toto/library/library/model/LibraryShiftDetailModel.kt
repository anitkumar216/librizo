package com.toto.library.library.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.jetbrains.annotations.NotNull

data class LibraryShiftDetailModel (
    val shiftName: String,
    val shiftStartTime: String,
    val shiftEndTime: String,
    val shiftFee: String
)
