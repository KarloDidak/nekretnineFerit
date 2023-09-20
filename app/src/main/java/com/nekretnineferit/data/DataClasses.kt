package com.nekretnineferit.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class House(
    var id: String,
    var userId: String,
    var price: Float,
    var location: String,
    var area: Float,
    var category: String,
    var description: String,
    var images: List<String>
): Parcelable {
    constructor():this("","",0f,"", 0f, "", "", emptyList())
}