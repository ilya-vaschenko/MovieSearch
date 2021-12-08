package com.example.moviesearch.viewmodel

import com.example.moviesearch.model.Film

sealed class AppState {
    data class Success(val filmsList: List<Film>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}