package com.example.moviesearch.model.repos

import com.example.moviesearch.model.database.HistoryDao
import com.example.moviesearch.model.database.HistoryEntity
import com.example.moviesearch.model.repos.LocalRepository

class LocalRepositoryImpl(private val dao: HistoryDao) : LocalRepository {

    override fun getAllHistory(): List<HistoryEntity> = dao.all()

    override fun saveEntity(film: HistoryEntity) {
        dao.insert(film)
    }
}