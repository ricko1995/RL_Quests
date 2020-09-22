package com.ricko.rlquests.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuest(quest: Quest)

    @Delete
    suspend fun deleteQuest(quest: Quest)

    @Query("SELECT * FROM quests_table ORDER BY creationDate DESC")
    fun getAllQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE isCompleted = 1 ORDER BY creationDate DESC")
    fun getCompletedQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE isCompleted = 0 ORDER BY creationDate DESC")
    fun getNotCompletedQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE questType = 0 ORDER BY creationDate DESC")
    fun getAllDailyQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE questType = 1 ORDER BY creationDate DESC")
    fun getAllWeeklyQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE questType = 2 ORDER BY creationDate DESC")
    fun getAllMonthlyQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE questType = 0 AND isCompleted = 1 ORDER BY creationDate DESC")
    fun getCompletedDailyQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE questType = 1 AND isCompleted = 1 ORDER BY creationDate DESC")
    fun getCompletedWeeklyQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE questType = 2 AND isCompleted = 1 ORDER BY creationDate DESC")
    fun getCompletedMonthlyQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE questType = 0 AND isCompleted = 0 ORDER BY creationDate DESC")
    fun getNotCompletedDailyQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE questType = 1 AND isCompleted = 0 ORDER BY creationDate DESC")
    fun getNotCompletedWeeklyQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE questType = 2 AND isCompleted = 0 ORDER BY creationDate DESC")
    fun getNotCompletedMonthlyQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE questType = 0 AND isCompleted = 1 AND completionDate BETWEEN :firstDate AND :lastDate ORDER BY creationDate DESC")
    fun getCompletedDailyQuestsBetweenDates(firstDate: Long, lastDate: Long): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE questType = 1 AND isCompleted = 1 AND completionDate BETWEEN :firstDate AND :lastDate ORDER BY creationDate DESC")
    fun getCompletedWeeklyQuestsBetweenDates(firstDate: Long, lastDate: Long): LiveData<List<Quest>>

    @Query("SELECT * FROM quests_table WHERE questType = 2 AND isCompleted = 1 AND completionDate BETWEEN :firstDate AND :lastDate")
    fun getCompletedMonthlyQuestsBetweenDates(firstDate: Long, lastDate: Long): LiveData<List<Quest>>
}