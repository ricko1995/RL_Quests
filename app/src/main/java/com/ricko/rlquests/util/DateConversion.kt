package com.ricko.rlquests.util

import android.annotation.SuppressLint
import com.ricko.rlquests.db.Quest
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.floor

object DateConversion {

    fun getCleanElapsedTime(quest: Quest): String {
        val questCreationDateMillis = quest.creationDate
        val elapsedTime = System.currentTimeMillis() - questCreationDateMillis
        val elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime)
        val elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime)
        val elapsedHours = TimeUnit.MILLISECONDS.toHours(elapsedTime)
        val elapsedDays = TimeUnit.MILLISECONDS.toDays(elapsedTime)
        val elapsedWeeks = floor(TimeUnit.MILLISECONDS.toDays(elapsedTime) / 7f).toLong()
        val elapsedYears = floor(TimeUnit.MILLISECONDS.toDays(elapsedTime) / 365f).toLong()

        return when {
            elapsedYears > 0 -> "${elapsedYears}y ago"
            elapsedWeeks > 0 -> "${elapsedWeeks}w ago"
            elapsedDays > 0 -> "${elapsedDays}d ago"
            elapsedHours > 0 -> "${elapsedHours}h ago"
            elapsedMinutes > 0 -> "${elapsedMinutes}min ago"
            elapsedSeconds > 0 -> "${elapsedSeconds}sec ago"
            else -> "Right now"
        }

    }

    @SuppressLint("SimpleDateFormat")
    fun getCleanCreatedDate(quest: Quest): String {
        val creationDateMillis = quest.creationDate
        val date = Date(creationDateMillis)
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return "Created on: ${sdf.format(date)}"
    }

    fun getQuestDuration(quest: Quest): String {
        val questDurationMillis = quest.completionDate?.minus(quest.creationDate)
        questDurationMillis?.let {
            val durationSeconds = TimeUnit.MILLISECONDS.toSeconds(it)
            val durationMinutes = TimeUnit.MILLISECONDS.toMinutes(it)
            val durationHours = TimeUnit.MILLISECONDS.toHours(it)
            val durationDays = TimeUnit.MILLISECONDS.toDays(it)
            val durationWeeks = floor(TimeUnit.MILLISECONDS.toDays(it) / 7f).toLong()
            val durationYears = floor(TimeUnit.MILLISECONDS.toDays(it) / 365f).toLong()

            return when {
                durationYears > 0 -> "${durationYears}y to complete."
                durationWeeks > 0 -> "${durationWeeks}w to complete."
                durationDays > 0 -> "${durationDays}d to complete."
                durationHours > 0 -> "${durationHours}h to complete."
                durationMinutes > 0 -> "${durationMinutes}min to complete."
                durationSeconds > 0 -> "${durationSeconds}sec to complete."
                else -> "Right now"
            }
        }
        return ""
    }

    @SuppressLint("SimpleDateFormat")
    fun getMonth(quest: Quest):Int{
        quest.completionDate?.let {
            val date = Date(it)
            val sdfDay = SimpleDateFormat("dd")
            val sdfMonth = SimpleDateFormat("MM")
            val sdfYear = SimpleDateFormat("yyyy")

            val day = sdfDay.format(date)
            val month = sdfMonth.format(date)
            val year = sdfYear.format(date)
            val calendar = Calendar.getInstance().also {calendar->
                calendar.firstDayOfWeek = Calendar.MONDAY
                calendar.set(year.toInt(), month.toInt(), day.toInt())
            }

            return calendar.get(Calendar.MONTH)

        }
        return -1
    }
}