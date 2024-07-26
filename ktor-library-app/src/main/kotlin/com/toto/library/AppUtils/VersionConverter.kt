package com.toto.library.AppUtils

object VersionConverter {
    fun versionToInt(version: String): Int {
        val parts = version.split(".")
        val major = parts.getOrNull(0)?.toInt() ?: 0
        val minor = parts.getOrNull(1)?.toInt() ?: 0
        val patch = parts.getOrNull(2)?.toInt() ?: 0

        return (major * 100) + (minor * 10) + patch
    }
}