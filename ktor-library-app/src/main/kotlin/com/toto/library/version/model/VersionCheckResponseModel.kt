package com.toto.library.version.model

data class VersionCheckResponseModel(
    val update: Boolean,
    val forceUpdate: Boolean,
    val appAccess: Boolean
)