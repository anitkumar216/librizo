package com.toto.library.common.model

data class GenericResponse<out T>(val isSuccess: Boolean, val data: T)