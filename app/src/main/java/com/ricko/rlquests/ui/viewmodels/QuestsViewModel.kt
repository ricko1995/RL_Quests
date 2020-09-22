package com.ricko.rlquests.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ricko.rlquests.db.Quest
import com.ricko.rlquests.db.QuestType
import com.ricko.rlquests.repositories.MainRepository
import kotlinx.coroutines.launch

class QuestsViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val quests: MediatorLiveData<List<Quest>> = MediatorLiveData()
    private val dailyQuests = mainRepository.getAllDailyQuests()
    private val weeklyQuests = mainRepository.getAllWeeklyQuests()
    private val monthlyQuests = mainRepository.getAllMonthlyQuests()

    var questType = QuestType.DAILY

    init {
        quests.addSource(dailyQuests) { result ->
            if (questType == QuestType.DAILY) {
                result?.let { quests.value = it }
            }
        }

        quests.addSource(weeklyQuests) { result ->
            if (questType == QuestType.WEEKLY) {
                result?.let { quests.value = it }
            }
        }

        quests.addSource(monthlyQuests) { result ->
            if (questType == QuestType.MONTHLY) {
                result?.let { quests.value = it }
            }
        }
    }

    fun setQuestType(questType: QuestType) = when (questType) {
        QuestType.DAILY -> dailyQuests.value?.let { quests.value = it }
        QuestType.WEEKLY -> weeklyQuests.value?.let { quests.value = it }
        QuestType.MONTHLY -> monthlyQuests.value?.let { quests.value = it }
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