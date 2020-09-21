package com.ricko.rlquests.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ricko.rlquests.db.Quest
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.floor

object CleanStrings {

    fun getCleanElapsedTime(quest: Quest): String {
        val questCreationDate = quest.creationDate
        val elapsedTime = System.currentTimeMillis() - questCreationDate
        val elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime)
        val elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime)
        val elapsedHours = TimeUnit.MILLISECONDS.toHours(elapsedTime)
        val elapsedDays = TimeUnit.MILLISECONDS.toDays(elapsedTime)
        val elapsedWeeks = floor(TimeUnit.MILLISECONDS.toDays(elapsedTime)/7f).toLong()
        val elapsedYears = floor(TimeUnit.MILLISECONDS.toDays(elapsedTime)/365f).toLong()

        return when{
            elapsedYears>0-> "${elapsedYears}y ago"
            elapsedWeeks>0-> "${elapsedWeeks}w ago"
            elapsedDays>0-> "${elapsedDays}d ago"
            elapsedHours>0-> "${elapsedHours}h ago"
            elapsedMinutes>0-> "${elapsedMinutes}min ago"
            elapsedSeconds>0-> "${elapsedSeconds}sec ago"
            else -> "Right now"
        }

    }

    @SuppressLint("SimpleDateFormat")
    fun getCleanCreatedDate(quest: Quest):String{
        val creationDate = quest.creationDate
        val date = Date(creationDate)
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm" )
        return "Created on: ${sdf.format(date)}"
    }
}