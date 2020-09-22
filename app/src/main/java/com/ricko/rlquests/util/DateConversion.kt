package com.ricko.rlquests.util

import android.annotation.SuppressLint
import com.prolificinteractive.materialcalendarview.CalendarDay
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
    fun getMonth(quest: Quest): Int {
        quest.completionDate?.let {
            val date = Date(it)
            val sdfDay = SimpleDateFormat("dd")
            val sdfMonth = SimpleDateFormat("MM")
            val sdfYear = SimpleDateFormat("yyyy")

            val day = sdfDay.format(date)
            val month = sdfMonth.format(date)
            val year = sdfYear.format(date)
            val calendar = Calendar.getInstance().also { calendar ->
                calendar.firstDayOfWeek = Calendar.MONDAY
                calendar.set(year.toInt(), month.toInt(), day.toInt())
            }

            return calendar.get(Calendar.MONTH)

        }
        return -1
    }

    fun getMonthDays(calendarDay: CalendarDay): List<CalendarDay> {
        val currentMonth = calendarDay.month
        val currentYear = calendarDay.year
        val lastDayOfCurrentMonth = when (currentMonth) {
            4, 6, 9, 11 -> 30
            2 -> if (currentYear % 4 == 0) 29 else 28
            else -> 31
        }
        val listOfDays = mutableListOf<CalendarDay>()
        for (i in 1..lastDayOfCurrentMonth) {
            listOfDays.add(CalendarDay.from(currentYear, currentMonth, i))
        }
        return listOfDays
    }

    fun getWeekDays(calendarDay: CalendarDay): List<CalendarDay> {
        val currentDay = calendarDay.day
        val currentMonth = calendarDay.month
        val currentYear = calendarDay.year
        val currentDayOfWeek = Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY
            set(currentYear, currentMonth - 1, currentDay)
        }.get(Calendar.DAY_OF_WEEK)

        val monday = currentDay - if (currentDayOfWeek == 1) currentDayOfWeek + 5 else currentDayOfWeek - 2

        val previousMonth = if (currentMonth == 1) 12 else currentMonth - 1
        val nextMonth = if (currentMonth == 12) 1 else currentMonth + 1

        val lastDayOfCurrentMonth = when (currentMonth) {
            4, 6, 9, 11 -> 30
            2 -> if (currentYear % 4 == 0) 29 else 28
            else -> 31
        }
        var lastDayOfPreviousMonth = when (previousMonth) {
            4, 6, 9, 11 -> 30
            2 -> if (currentYear % 4 == 0) 29 else 28
            else -> 31
        }
        var nextMonthDay = 1

        val listOfDays = mutableListOf<CalendarDay>()
        for (i in monday..monday + 6) {
            if (i in 1..lastDayOfCurrentMonth) {
                listOfDays.add(CalendarDay.from(currentYear, currentMonth, i))
            }
            if (i > lastDayOfCurrentMonth) {
                val year = if (currentMonth == 12) currentYear + 1 else currentYear
                listOfDays.add(CalendarDay.from(year, nextMonth, nextMonthDay))
                nextMonthDay++
            }
            if (i < 1) {
                val year = if (currentMonth == 1) currentYear - 1 else currentYear
                listOfDays.add(CalendarDay.from(year, previousMonth, lastDayOfPreviousMonth))
                lastDayOfPreviousMonth--
            }
        }

        return listOfDays
    }

    fun getMillisFromListOfDates(listOfDates: List<CalendarDay>): Pair<Long, Long> {
        val firstDate = Calendar.getInstance().apply {
            set(listOfDates.first().year, listOfDates.first().month - 1, listOfDates.first().day, 12, 0, 0)
        }.timeInMillis

        val lastDate = Calendar.getInstance().apply {
            set(listOfDates.last().year, listOfDates.last().month - 1, listOfDates.last().day, 12, 0, 0)
        }.timeInMillis

        return Pair(firstDate, lastDate)
    }

    fun getOneDayMillis(calendarDay: CalendarDay): Pair<Long, Long> {
        val startOfDay = Calendar.getInstance().apply {
            set(calendarDay.year, calendarDay.month - 1, calendarDay.day, 0, 0, 1)
        }.timeInMillis

        val endOfDay = Calendar.getInstance().apply {
            set(calendarDay.year, calendarDay.month - 1, calendarDay.day, 23, 59, 59)
        }.timeInMillis

        return Pair(startOfDay, endOfDay)
    }
}