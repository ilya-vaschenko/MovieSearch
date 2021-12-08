package com.example.moviesearch.model.repos

import com.example.moviesearch.model.database.HistoryEntity

interface LocalRepository {
    fun getAllHistory(): List<HistoryEntity>
    fun saveEntity(film: HistoryEntity)
}