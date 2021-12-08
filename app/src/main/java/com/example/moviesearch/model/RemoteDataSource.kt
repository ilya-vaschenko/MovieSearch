package com.example.moviesearch.model

import com.example.moviesearch.BuildConfig
import com.example.moviesearch.model.retrofit.ApiUtils
import com.example.moviesearch.model.retrofit.FilmApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    private val filmApi = Retrofit.Builder()
        .baseUrl(ApiUtils.baseUrl)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .client(ApiUtils.getOkHTTPBuilderWithHeaders())
        .build().create(FilmApi::class.java)

    fun getFilmFromRemoteDataSourceByOk(
        link: String,
        callback: okhttp3.Callback
    ) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(link)
            .addHeader("key_API", BuildConfig.FILM_API_KEY)
            .get()
            .build()

        client.newCall(request).enqueue(callback)
    }

    fun getPopularFilmsFromRemoteDataSource(callback: Callback<FilmModel>) {
        filmApi.getPopularFilm(BuildConfig.FILM_API_KEY, "ru-RU").enqueue(callback)
    }
}