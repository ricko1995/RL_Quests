package com.ricko.rlquests.db

import androidx.room.TypeConverter


class Converters {
    @TypeConverter
    fun toQuestType(value: Int): QuestType {
        return when (value) {
            0 -> QuestType.DAILY
            1 -> QuestType.WEEKLY
            2 -> QuestType.MONTHLY
            else -> QuestType.DAILY
        }
    }

    @TypeConverter
    fun fromQuestType(value: QuestType): Int? {
        return when (value) {
            QuestType.DAILY -> 0
            QuestType.WEEKLY -> 1
            QuestType.MONTHLY -> 2
        }
    }
}