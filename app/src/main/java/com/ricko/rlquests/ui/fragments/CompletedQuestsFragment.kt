package com.ricko.rlquests.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ricko.rlquests.R
import kotlinx.android.synthetic.main.fragment_completed_quests.*
import java.util.*

class CompletedQuestsFragment : Fragment(R.layout.fragment_completed_quests) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        calendarView.setOnDateChangedListener { widget, date, selected ->
            val cal = Calendar.getInstance().also {
                it.firstDayOfWeek = Calendar.TUESDAY
                it.set(date.year, date.month-1, date.day) }
            val day = cal.get(Calendar.DAY_OF_WEEK)
            println(day)
        }
//        calendarView.setOnDateChangeListener { calendarView, year, month, day ->
//            val quest = Quest(creationDate = System.currentTimeMillis(), questType = QuestType.DAILY)
//            println(DateConversion.getMonth(quest))
//            val calendar = Calendar.getInstance()
//            calendar.firstDayOfWeek = Calendar.MONDAY
//
//            calendar.set(year, month, day)
//            val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
//            val monthOfYear = calendar.get(Calendar.MONTH)
//            println(monthOfYear)
//            val dateString = "${day}.${month+1}.${year}"
//            val sdf = SimpleDateFormat("dd.mm.yyyy")
//            val date = sdf.parse(dateString)
//            val dateMillis = date?.time
////            println(dateMillis)
////            println(sdf.format(dateMillis))
//
//        }

        var bool = false
        btnTest.setOnClickListener {
            bool = if (bool) {
                appBar.setExpanded(false)
                !bool
            } else {
                appBar.setExpanded(true)
                !bool
            }
        }
    }
}