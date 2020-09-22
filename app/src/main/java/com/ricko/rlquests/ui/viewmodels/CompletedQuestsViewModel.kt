package com.ricko.rlquests.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.ricko.rlquests.db.Quest
import com.ricko.rlquests.db.QuestType
import com.ricko.rlquests.repositories.MainRepository

class CompletedQuestsViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private var dateRangeMillis = Pair(0L, Long.MAX_VALUE)

    val quests: MediatorLiveData<List<Quest>> = MediatorLiveData()

    var questType = MutableLiveData(QuestType.DAILY)

    var lastSelectedDate = CalendarDay.today()

    init {
        updateQuestsSource()
    }

    private fun updateQuestsSource() {
        quests.addSource(
            mainRepository.getCompletedDailyQuestsBetweenDates(
                dateRangeMillis.first,
                dateRangeMillis.second
            )
        ) { result ->
            if (questType.value == QuestType.DAILY) {
                result?.let { quests.value = it }
            }
        }

        quests.addSource(
            mainRepository.getCompletedWeeklyQuestsBetweenDates(
                dateRangeMillis.first,
                dateRangeMillis.second
            )
        ) { result ->
            if (questType.value == QuestType.WEEKLY) {
                result?.let { quests.value = it }
            }
        }

        quests.addSource(
            mainRepository.getCompletedMonthlyQuestsBetweenDates(
                dateRangeMillis.first,
                dateRangeMillis.second
            )
        ) { result ->
            if (questType.value == QuestType.MONTHLY) {
                result?.let { quests.value = it }
            }
        }
    }

    fun setupFirstLastDay(millis: Pair<Long, Long>) {
        dateRangeMillis = millis
//        firstDate = millis.first
//        lastDate = millis.second
        updateQuestsSource()
    }

    fun onToggleBtnClick(qt: QuestType) {
        questType.value = qt
        updateQuestsSource()
    }
}