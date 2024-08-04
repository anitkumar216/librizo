package example.com.notes.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class NotesPayloadModel(
    @BsonId
    val _id: String? = ObjectId().toString(),
    val title: String?,
    val categories: ArrayList<String>?,
    val body: String,
    val author: String
)
