package com.ricko.rlquests.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Quest::class],
    version = 7
)
@TypeConverters(Converters::class)
abstract class QuestsDatabase : RoomDatabase() {
    abstract fun getQuestDao(): QuestDao
}