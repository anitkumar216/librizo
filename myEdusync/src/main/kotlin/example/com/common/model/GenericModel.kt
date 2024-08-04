package example.com.common.model

data class GenericResponse<out T>(val isSuccess: Boolean, val data: T)