package com.example.moviesearch.view

import android.app.Application
import androidx.room.Room
import com.example.moviesearch.model.database.HistoryDao
import com.example.moviesearch.model.database.HistoryDataBase


class App : Application() {

    override fun onCreate() { //запустится до того, как вызовется активити
        super.onCreate()
        appInstance = this
    }


    companion object {
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null

        private val DB_NAME = "History.db" //имя файла, в который будет сохр база данных

        fun getHistoryDao(): HistoryDao {

            if (db == null) {
                if (appInstance == null) throw IllegalStateException("Что-то пошло не так")

                db = Room.databaseBuilder( // инициализируем базу данных
                    appInstance!!,           //передаем контекст
                    HistoryDataBase::class.java, // передаем тип
                    DB_NAME //передаем имя

                )
                    .build()

            }
            return db!!.historyDao()
        }
    }

}