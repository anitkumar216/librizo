package example.com.common.repository

import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class DatabaseFactory {
    private val client = KMongo.createClient().coroutine
    val database = client.getDatabase("MyEduSyncDB")
}