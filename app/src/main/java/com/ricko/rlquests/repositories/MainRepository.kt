package com.ricko.rlquests.repositories

import com.ricko.rlquests.db.Quest
import com.ricko.rlquests.db.QuestDao
import javax.inject.Inject

class MainRepository @Inject constructor(private val questDao: QuestDao) {

    suspend fun insertQuest(quest: Quest) = questDao.insertQuest(quest)

    suspend fun deleteQuest(quest: Quest) = questDao.deleteQuest(quest)

    fun getAllQuests() = questDao.getAllQuests()

    fun getCompletedQuests() = questDao.getCompletedQuests()

    fun getNotCompletedQuests() = questDao.getNotCompletedQuests()

    fun getAllDailyQuests() = questDao.getAllDailyQuests()

    fun getAllWeeklyQuests() = questDao.getAllWeeklyQuests()

    fun getAllMonthlyQuests() = questDao.getAllMonthlyQuests()

    fun getCompletedDailyQuests() = questDao.getCompletedDailyQuests()

    fun getCompletedWeeklyQuests() = questDao.getCompletedWeeklyQuests()

    fun getCompletedMonthlyQuests() = questDao.getCompletedMonthlyQuests()

    fun getNotCompletedDailyQuests() = questDao.getNotCompletedDailyQuests()

    fun getNotCompletedWeeklyQuests() = questDao.getNotCompletedWeeklyQuests()

    fun getNotCompletedMonthlyQuests() = questDao.getNotCompletedMonthlyQuests()
}