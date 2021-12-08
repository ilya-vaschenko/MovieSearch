package com.example.moviesearch.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val timestamp: Long,
    val notes: String
)