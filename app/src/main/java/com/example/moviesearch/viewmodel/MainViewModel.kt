package com.example.moviesearch.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.model.Film
import com.example.moviesearch.model.FilmDTO
import com.example.moviesearch.model.FilmModel
import com.example.moviesearch.model.RemoteDataSource
import com.example.moviesearch.model.repos.Repository
import com.example.moviesearch.model.repos.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

) :
    ViewModel() {

    private val repository: Repository = RepositoryImpl(RemoteDataSource())

    val liveData: LiveData<AppState> = liveDataToObserve

    fun getFilmFromRemoteDataSource(adult: Boolean) {
        liveDataToObserve.value = AppState.Loading

        repository.getPopularFilmsByRetro(object : Callback<FilmModel> {

            override fun onFailure(call: Call<FilmModel>, t: Throwable) {
                Log.d("fff", t.toString())
                liveDataToObserve.postValue(AppState.Error(t))
            }

            override fun onResponse(call: Call<FilmModel>, response: Response<FilmModel>) {
                if (!adult) {
                    response.body()?.let {
                        checkResponse(it.result)
                        liveDataToObserve.postValue(checkResponse(showFilmsWithoutAdult(it.result)))
                    }
                }
                response.body()?.let {
                    liveDataToObserve.postValue(checkResponse(it.result))
                }
            }
        })
    }

    private fun checkResponse(filmDTO: List<FilmDTO>): AppState {
        val filmsList = mutableListOf<Film>()
        filmDTO.forEach {
            filmsList.add(
                Film(
                    name = it.title ?: "",
                    id = it.id ?: 0,
                    date = it.release_date ?: "",
                    genre = "",
                    description = it.overview ?: "",
                    posterPath = it.poster_path ?: ""
                )
            )
        }

        return AppState.Success(filmsList)

    }

    private fun showFilmsWithoutAdult(filmsList: List<FilmDTO>): List<FilmDTO> {
        val listWithoutAdult = mutableListOf<FilmDTO>()
        filmsList.forEach {
            if (!it.adult) {
                listWithoutAdult.add(it)
            }
        }
        return listWithoutAdult
    }
}


