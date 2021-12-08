package com.example.moviesearch.model.repos

import com.example.moviesearch.model.Film
import com.example.moviesearch.model.FilmModel

interface Repository {
    fun getFilmFromServer(): Film
//    fun getFilmFromLocalStorageRus(): List<Film>
//    fun getFilmFromLocalStorageWorld(): List<Film>

    fun getPopularFilmsByRetro(callback: retrofit2.Callback<FilmModel>)

}