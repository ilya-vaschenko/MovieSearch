package com.example.moviesearch.view

import android.app.Application
import androidx.room.Room
import com.example.moviesearch.model.database.HistoryDao
import com.example.moviesearch.model.database.HistoryDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private val DB_NAME = "History.db"

        fun getHistoryDao(): HistoryDao {

            if (db == null) {
                if (appInstance == null) throw IllegalStateException("Что-то пошло не так")

                db = Room.databaseBuilder(
                    appInstance!!,
                    HistoryDataBase::class.java,
                    DB_NAME
                )
                    .build()
            }
            return db!!.historyDao()
        }
    }
}