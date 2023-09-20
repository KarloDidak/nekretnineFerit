package com.nekretnineferit.firebase

enum class MESSAGE_TYPE {
    FIREBASE_LOADING,
    FIREBASE_SUCCESS,
    FIREBASE_ERROR,
    VALIDATION_OK,
    VALIDATION_EMAIL_ERROR,
    VALIDATION_PASSWORD_ERROR
}

data class FlowMessage<T>(
    val data: T? = null,
    val type: MESSAGE_TYPE,
    val message: String? = null
)
