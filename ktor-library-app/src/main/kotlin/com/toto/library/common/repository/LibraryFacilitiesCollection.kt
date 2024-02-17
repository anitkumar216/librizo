package com.toto.library.common.repository

import com.toto.library.facilities.model.FacilitiesResponseModel
import com.toto.library.library.model.LibraryDetailsDataPayloadModel
import org.litote.kmongo.coroutine.CoroutineCollection

class LibraryFacilitiesCollection {
    private val databaseFactory = DatabaseFactory()
    private val facilitiesCollection: CoroutineCollection<FacilitiesResponseModel> =
        databaseFactory.database.getCollection("libraryFacilitiesList")

    suspend fun getAllLibraryFacilities(): List<FacilitiesResponseModel> = facilitiesCollection.find().toList()

}