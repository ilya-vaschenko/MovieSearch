package com.example.moviesearch.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HistoryEntity::class],
    version = 1,
    exportSchema = true
)  //передаем наши таблицы, передаем версию, экспортшема-показывает типа логов, кто куда ходил
abstract class HistoryDataBase : RoomDatabase() { //сам файл, в котором будет храниться таблица


    abstract fun historyDao(): HistoryDao //будет возвращать дао

}