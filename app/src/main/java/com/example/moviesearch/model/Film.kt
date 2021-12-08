package com.example.moviesearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    var id: Long = 550,
    var name: String = "",
    var genre: String = "",
    var date: String = "",
    var imageIndex: Int = 0,
    var description: String = "Нет описания",
    val posterPath: String? = "",
    val adult: Boolean = false,
    var note: String = "",
    val country: String = ""
) : Parcelable

