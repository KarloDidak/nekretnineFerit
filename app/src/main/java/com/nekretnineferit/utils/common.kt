package com.nekretnineferit.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import com.nekretnineferit.firebase.MESSAGE_TYPE.*
import com.nekretnineferit.R
import com.nekretnineferit.activities.BrowsingActivity
import com.nekretnineferit.firebase.FlowMessage
import com.nekretnineferit.firebase.userGetCurrent

fun loadImages (imagePickerResult: ActivityResult): MutableList<String>
{
    val selectedImages = mutableListOf<String>()

    if (imagePickerResult.resultCode == Activity.RESULT_OK) {
        val intent = imagePickerResult.data
        selectedImages.clear()

        if (intent?.clipData != null) {
            val count = intent.clipData?.itemCount ?: 0
            (0 until count).forEach { it ->
                val imageUri = intent.clipData?.getItemAt(it)?.uri
                imageUri?.let { selectedImages.add(it.toString()) }
            }
        } else {
            val imageUri = intent?.data
            imageUri?.let { selectedImages.add(it.toString()) }
        }
    }

    return selectedImages
}

fun showUser(fragment: Fragment, name: TextView, email: TextView?, imageView: ImageView) {
    val currUser = userGetCurrent() ?: return

    for (profile in currUser.providerData) {
        name.text = profile.displayName
        email?.text = profile.email
        if (profile.photoUrl != null)
            Glide.with(fragment)
                .load(profile.photoUrl)
                .error(ColorDrawable(Color.BLACK)).into(imageView)
    }
}

fun collectUserSigningFlow (
    fragment: Fragment,
    flowMessage: FlowMessage<FirebaseUser>,
    mailEdit: EditText,
    passwordEdit: EditText,
    showLoading: () -> Unit,
    hideLoading: () -> Unit
)
{
    if (flowMessage.type == FIREBASE_LOADING)
        showLoading()
    else {
        hideLoading()
        if (flowMessage.type == FIREBASE_SUCCESS)
            Intent(fragment.requireActivity(), BrowsingActivity::class.java).also { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                fragment.startActivity(intent)
            }
        else
            showUserSigningError(fragment.requireActivity(), flowMessage, mailEdit, passwordEdit)
    }
}

fun showUserSigningError (
    context: Context,
    flowMessage: FlowMessage<FirebaseUser>,
    mailEdit: EditText,
    passwordEdit: EditText)
{
    when (flowMessage.type) {
        FIREBASE_ERROR ->
            showErrorDialog(context, flowMessage.message!!)

        VALIDATION_EMAIL_ERROR -> {
            mailEdit.requestFocus()
            mailEdit.error = flowMessage.message
        }

        VALIDATION_PASSWORD_ERROR -> {
            passwordEdit.requestFocus()
            passwordEdit.error = flowMessage.message
        }

        else -> Unit
    }
}

fun showConfirmationDialog(context: Context, title: String, message: String, positive: () -> Unit) {
    val strGoBack = context.getString(R.string.btn_back)
    val strConfirm = context.getString(R.string.btn_confirm)

    val alertDialog = AlertDialog.Builder(context).apply {
        setTitle(title)
        setMessage(message)
        setNegativeButton(strGoBack) { dialog, _ ->
            dialog.dismiss()
        }
        setPositiveButton(strConfirm) { dialog, _ ->
            positive()
            dialog.dismiss()
        }
    }
    alertDialog.create()
    alertDialog.show()
}

fun showErrorDialog(context: Context, message: String) {
    val strError = context.getString(R.string.error)
    val strGoBack = context.getString(R.string.btn_back)

    val alertDialog = AlertDialog.Builder(context).apply {
        setTitle(strError)
        setMessage(message)
        setPositiveButton(strGoBack) { dialog, _ ->
            dialog.dismiss()
        }
    }
    alertDialog.create()
    alertDialog.show()
}