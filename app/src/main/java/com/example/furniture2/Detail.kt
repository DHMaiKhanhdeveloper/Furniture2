package com.example.furniture2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Detail(
    val imageDetails: Int,
    var titleDetails: String,
    var DetailsResourceId: Int
): Parcelable