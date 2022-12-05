package com.example.furniture2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Furniture(
    val imageFurniture: Int,
    var titleFurniture: String,
    val details: List<Detail>

): Parcelable