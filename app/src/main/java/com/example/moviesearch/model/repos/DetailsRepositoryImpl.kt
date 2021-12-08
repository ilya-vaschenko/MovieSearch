package com.example.moviesearch.model.repos

import com.example.moviesearch.model.RemoteDataSource
import okhttp3.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    DetailsRepository {
    override fun getFilmByOkHttp(requestLink: String, callback: Callback) {
        remoteDataSource.getFilmFromRemoteDataSourceByOk(requestLink, callback)
    }
}