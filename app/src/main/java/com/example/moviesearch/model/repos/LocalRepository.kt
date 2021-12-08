package com.example.moviesearch.model.repos

import com.example.moviesearch.model.database.HistoryEntity


interface LocalRepository { //класс, который будет отвечать за работу с локальными данными
    fun getAllHistory(): List<HistoryEntity>
    fun saveEntity(film: HistoryEntity)
}