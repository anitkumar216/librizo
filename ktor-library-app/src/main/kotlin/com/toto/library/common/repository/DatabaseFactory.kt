package com.toto.library.common.repository

import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class DatabaseFactory {
    private val client = KMongo.createClient().coroutine
    val database = client.getDatabase("librarydb")
}