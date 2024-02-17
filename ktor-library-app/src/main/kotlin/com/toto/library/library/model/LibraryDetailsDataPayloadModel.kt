package com.toto.library.library.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class LibraryDetailsDataPayloadModel(
    @BsonId
    val _id: String? = ObjectId().toString(),
    @BsonId
    val emailId: String?,
    @BsonId
    val phoneNumber: String?,
    val libraryName: String?,
    val libraryAddress: String?,
    val totalSeat: String?,
    val registrationFee: String?,
    val securityDeposit: String?,
    val shiftDetail: ArrayList<LibraryShiftDetailModel>?,
    val openingTiming: String?,
    val closingTiming: String?,
    val libraryType: String?,
    val libraryFacilities: ArrayList<String>?,
    //val libraryImages
    val ownerAadharNumber: String?,
    val libraryLocation: LocationModel?
)