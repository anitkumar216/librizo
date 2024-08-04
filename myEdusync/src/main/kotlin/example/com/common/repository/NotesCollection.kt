package example.com.common.repository

import example.com.notes.model.NotesPayloadModel
import org.litote.kmongo.coroutine.CoroutineCollection

class NotesCollection {
    private val databaseFactory = DatabaseFactory()
    private val notesCollection: CoroutineCollection<NotesPayloadModel> =
        databaseFactory.database.getCollection("notesCollection")

    suspend fun addNotesDetails(libraryDetails: NotesPayloadModel): NotesPayloadModel {
        notesCollection.insertOne(libraryDetails)
        return libraryDetails
    }

    /*suspend fun getAllLibrary(): List<LibraryDetailsDataPayloadModel> = studentCollection.find().toList()

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
    }*/
}