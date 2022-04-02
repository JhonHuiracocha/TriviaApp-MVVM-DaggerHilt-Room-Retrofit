package com.multi.trivia.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "scores")
data class Score(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "score_id") val id: Long,
    @ColumnInfo(name = "points") val points: Int,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "date") val date: Date?
)