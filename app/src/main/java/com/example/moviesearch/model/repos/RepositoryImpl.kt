package com.example.moviesearch.model.repos

import com.example.moviesearch.model.Film
import com.example.moviesearch.model.FilmModel
import com.example.moviesearch.model.RemoteDataSource
import retrofit2.Callback

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {
    override fun getFilmFromServer(): Film = Film()
    override fun getPopularFilmsByRetro(callback: Callback<FilmModel>) {
        remoteDataSource.getPopularFilmsFromRemoteDataSource(callback)
    }
}