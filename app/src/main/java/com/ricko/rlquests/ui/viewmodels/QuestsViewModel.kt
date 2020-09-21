package com.ricko.rlquests.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ricko.rlquests.db.Quest
import com.ricko.rlquests.db.QuestType
import com.ricko.rlquests.repositories.MainRepository
import kotlinx.coroutines.launch

class QuestsViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val quest: MediatorLiveData<List<Quest>> = MediatorLiveData()
    private val dailyQuests = mainRepository.getAllDailyQuests()
    private val weeklyQuests = mainRepository.getAllWeeklyQuests()
    private val monthlyQuests = mainRepository.getAllMonthlyQuests()
//    val completedQuests = mainRepository.getCompletedQuests()
//    val notCompletedQuests = mainRepository.getNotCompletedQuests()
    var questType = QuestType.DAILY

    init {
        quest.addSource(dailyQuests) { result ->
            if (questType == QuestType.DAILY) {
                result?.let { quest.value = it }
            }
        }

        quest.addSource(weeklyQuests) { result ->
            if (questType == QuestType.WEEKLY) {
                result?.let { quest.value = it }
            }
        }

        quest.addSource(monthlyQuests) { result ->
            if (questType == QuestType.MONTHLY) {
                result?.let { quest.value = it }
            }
        }
    }

    fun sortRuns(questType: QuestType) = when (questType) {
        QuestType.DAILY -> dailyQuests.value?.let { quest.value = it }
        QuestType.WEEKLY -> weeklyQuests.value?.let { quest.value = it }
        QuestType.MONTHLY -> monthlyQuests.value?.let { quest.value = it }
    }.also {
        this.questType = questType
    }

    fun insertQuest(quest: Quest) = viewModelScope.launch {
        mainRepository.insertQuest(quest)
    }

    fun deleteQuest(quest: Quest) = viewModelScope.launch {
        mainRepository.deleteQuest(quest)
    }

}