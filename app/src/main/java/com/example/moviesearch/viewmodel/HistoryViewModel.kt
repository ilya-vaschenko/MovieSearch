package com.example.moviesearch.viewmodel

import androidx.lifecycle.ViewModel
import com.example.moviesearch.model.database.HistoryEntity
import com.example.moviesearch.model.repos.LocalRepositoryImpl
import com.example.moviesearch.view.App

class HistoryViewModel : ViewModel() {

    private val historyRepository = LocalRepositoryImpl(App.getHistoryDao())

    fun getAllHistory(): List<HistoryEntity> = historyRepository.getAllHistory()

}