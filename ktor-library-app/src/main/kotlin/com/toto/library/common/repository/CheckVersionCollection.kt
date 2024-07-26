package com.toto.library.common.repository

import com.toto.library.version.model.GetVersionResponseModel
import com.toto.library.version.model.VersionCheckResponseModel
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineCollection
class CheckVersionCollection {
    private val databaseFactory = DatabaseFactory()
    private val checkVersionCollection: CoroutineCollection<GetVersionResponseModel> =
        databaseFactory.database.getCollection("CheckVersionCollection")

    suspend fun checkIOSVersion(version: Int): VersionCheckResponseModel {
        val iOSCurrentVersion = checkVersionCollection.findOne()?.iOSVersion!!
        return when (iOSCurrentVersion) {
            version -> {
                VersionCheckResponseModel(update = false, forceUpdate = false, appAccess = true)
            }
            version+1 -> {
                VersionCheckResponseModel(update = true, forceUpdate = false, appAccess = false)
            }
            else -> {
                VersionCheckResponseModel(update = false, forceUpdate = true, appAccess = false)
            }
        }
    }

    suspend fun checkWebVersion(version: Int): VersionCheckResponseModel {
        val webCurrentVersion = checkVersionCollection.findOne()?.webVersion!!
        return when (webCurrentVersion) {
            version -> {
                VersionCheckResponseModel(update = false, forceUpdate = false, appAccess = true)
            }
            version+1 -> {
                VersionCheckResponseModel(update = true, forceUpdate = false, appAccess = false)
            }
            else -> {
                VersionCheckResponseModel(update = false, forceUpdate = true, appAccess = false)
            }
        }
    }
    suspend fun checkAndroidVersion(version: Int): VersionCheckResponseModel {
        val androidCurrentVersion = checkVersionCollection.findOne()?.androidVersion!!
        return when (androidCurrentVersion) {
            version -> {
                VersionCheckResponseModel(update = false, forceUpdate = false, appAccess = true)
            }
            version+1 -> {
                VersionCheckResponseModel(update = true, forceUpdate = false, appAccess = false)
            }
            else -> {
                VersionCheckResponseModel(update = false, forceUpdate = true, appAccess = false)
            }
        }
    }
}