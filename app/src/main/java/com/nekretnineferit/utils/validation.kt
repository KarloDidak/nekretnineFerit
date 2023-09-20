package com.nekretnineferit.utils

import android.util.Patterns
import com.nekretnineferit.NekretnineFeritApp
import com.nekretnineferit.R
import com.nekretnineferit.firebase.MESSAGE_TYPE
import com.nekretnineferit.firebase.MESSAGE_TYPE.*
import com.nekretnineferit.utils.Constants.EMPTY

data class Message (
    val type: MESSAGE_TYPE,
    val message: String = EMPTY
)

fun validateEmail(email: String): Message {
    val strErrorEmailEmpty = NekretnineFeritApp.context!!.getString(R.string.error_email_empty)
    val strErrorEmailInvalid = NekretnineFeritApp.context!!.getString(R.string.error_email_invalid)

    if (email.isEmpty())
        return Message(VALIDATION_EMAIL_ERROR, strErrorEmailEmpty)

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return Message(VALIDATION_EMAIL_ERROR, strErrorEmailInvalid)

    return Message(VALIDATION_OK)
}

fun validatePassword(password: String): Message {
    val strErrorPasswordEmpty = NekretnineFeritApp.context!!.getString(R.string.error_password_empty)
    val strErrorPasswordInvalid = NekretnineFeritApp.context!!.getString(R.string.error_password_invalid)

    if (password.isEmpty())
        return Message(VALIDATION_PASSWORD_ERROR, strErrorPasswordEmpty)

    if (password.length < 6)
        return Message(VALIDATION_PASSWORD_ERROR, strErrorPasswordInvalid)

    return Message(VALIDATION_OK)
}

fun validateUserInputs(email: String, password: String): Message {
    val emailValidation = validateEmail(email)
    if (emailValidation.type != VALIDATION_OK)
        return emailValidation

    val passwordValidation = validatePassword(password)
    if (passwordValidation.type != VALIDATION_OK)
        return passwordValidation

    return Message(VALIDATION_OK)
}

fun validateHouseInputs(
    price: String,
    location: String,
    area: String,
    category: String,
    description: String,
    images: List<String>
): Message {
    val strErrorPriceEmpty = NekretnineFeritApp.context!!.getString(R.string.error_price_empty)
    val strErrorLocationEmpty = NekretnineFeritApp.context!!.getString(R.string.error_location_empty)
    val strErrorAreaEmpty = NekretnineFeritApp.context!!.getString(R.string.error_area_empty)
    val strErrorCategoryEmpty = NekretnineFeritApp.context!!.getString(R.string.error_category_empty)
    val strErrorDescriptionEmpty = NekretnineFeritApp.context!!.getString(R.string.error_description_empty)
    val strErrorImagesEmpty = NekretnineFeritApp.context!!.getString(R.string.error_images_empty)

    if (price == EMPTY)
        return Message(VALIDATION_PASSWORD_ERROR, strErrorPriceEmpty)

    if (location == EMPTY)
        return Message(VALIDATION_PASSWORD_ERROR, strErrorLocationEmpty)

    if (area == EMPTY || area.toFloat() <= 0.0)
        return Message(VALIDATION_PASSWORD_ERROR, strErrorAreaEmpty)

    if (category == EMPTY)
        return Message(VALIDATION_PASSWORD_ERROR, strErrorCategoryEmpty)

    if (description == EMPTY)
        return Message(VALIDATION_PASSWORD_ERROR, strErrorDescriptionEmpty)

    if (images.isEmpty())
        return Message(VALIDATION_PASSWORD_ERROR, strErrorImagesEmpty)

    return Message(VALIDATION_OK)
}