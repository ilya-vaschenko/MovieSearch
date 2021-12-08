package com.example.moviesearch.model.repos

import okhttp3.Callback

interface DetailsRepository {
    fun getFilmByOkHttp(request: String, callback: Callback)

}