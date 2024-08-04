package com.toto.library.common.repository

import com.mongodb.client.MongoDatabase
import com.toto.library.version.model.GetVersionResponseModel
import com.toto.library.version.model.GetSalesResponseModel
import org.litote.kmongo.coroutine.CoroutineCollection
class CheckVersionCollection(database: MongoDatabase) {
    private val databaseFactory = DatabaseFactory()
    private val salesCollection: CoroutineCollection<List<GetSalesResponseModel>> =
        databaseFactory.database.getCollection("sales")

    suspend fun getSalesData(): List<GetSalesResponseModel> {
        val sales = salesCollection.findOne()
        return sales!!
    }
}