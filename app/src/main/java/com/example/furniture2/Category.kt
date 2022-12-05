package com.example.furniture2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val imageResource: Int,
    var title: String,
    val furnitures: List<Furniture>

): Parcelable