package com.multi.trivia.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class Score(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "score_id") val id: Long,
    @ColumnInfo(name = "name") val name: String
)