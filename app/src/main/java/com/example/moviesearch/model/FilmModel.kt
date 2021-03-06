package com.example.moviesearch.model

import com.google.gson.annotations.SerializedName

data class FilmModel(

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val result: List<FilmDTO>,
)
