package com.toto.library.common.repository

import com.toto.library.library.model.LibraryDetailsDataPayloadModel
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq

class LibraryDetailsCollection {

    private val databaseFactory = DatabaseFactory()
    private val studentCollection: CoroutineCollection<LibraryDetailsDataPayloadModel> =
        databaseFactory.database.getCollection("librariesDetailCollection")

    suspend fun addLibraryDetails(libraryDetails: LibraryDetailsDataPayloadModel): LibraryDetailsDataPayloadModel {
        studentCollection.insertOne(libraryDetails)
        return libraryDetails
    }

    suspend fun getAllLibrary(): List<LibraryDetailsDataPayloadModel> = studentCollection.find().toList()

    suspend fun getLibraryById(id: String): List<LibraryDetailsDataPayloadModel> {
        return studentCollection.find(LibraryDetailsDataPayloadModel::_id eq id).toList()
    }

    suspend fun getLibraryByEmailId(emailId: String): List<LibraryDetailsDataPayloadModel> {
        return studentCollection.find(LibraryDetailsDataPayloadModel::emailId eq emailId).toList()
    }

    suspend fun getLibraryByPhoneNumber(phoneNumber: String): List<LibraryDetailsDataPayloadModel> {
        return studentCollection.find(LibraryDetailsDataPayloadModel::phoneNumber eq phoneNumber).toList()
    }

    suspend fun deleteLibraryById(id: String): Boolean {
        return studentCollection.deleteOne(LibraryDetailsDataPayloadModel::_id eq id).wasAcknowledged()
    }

    suspend fun updateLibraryById(id: String, user: LibraryDetailsDataPayloadModel): Boolean {
        return studentCollection.updateOne(LibraryDetailsDataPayloadModel::_id eq id, user).wasAcknowledged()
    }
}