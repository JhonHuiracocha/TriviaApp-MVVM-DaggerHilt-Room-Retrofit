package com.multi.trivia.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val score: Int
) : Parcelable
