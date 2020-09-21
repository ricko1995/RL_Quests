package com.ricko.rlquests.other

import com.ricko.rlquests.db.Quest
import com.ricko.rlquests.db.QuestType
import kotlin.random.Random

object TestQuests {

    fun listOfFakeQuests(currentTime: Long):Quest{
        val q = listOf(
            Quest("Clean Shit", "You must clean shit because you will be punished", false, currentTime, questType = QuestType.DAILY),
            Quest("Take Shit", "You must take shit because you will explode", false, currentTime, questType = QuestType.DAILY),
            Quest("Feed Cat", "You must feed cat because cat will die... witch is not so bad..", false, currentTime, questType = QuestType.DAILY),
            Quest("Study", "You must study because you will fail", false, currentTime, questType = QuestType.DAILY),
            Quest("Work", "You must work because you will be poor", false, currentTime, questType = QuestType.DAILY),
            Quest("Say Hi To Girlfriend", "You must say hi to girlfriend because you will be punished", false, currentTime, questType = QuestType.DAILY),
            Quest("Work out", "You must work out because you will be weak", false, currentTime, questType = QuestType.DAILY),
            Quest("Clean Shit", "You must clean shit because you will be punished", false, currentTime, questType = QuestType.WEEKLY),
            Quest("Take Shit", "You must take shit because you will explode", false, currentTime, questType = QuestType.WEEKLY),
            Quest("Feed Cat", "You must feed cat because cat will die... witch is not so bad..", false, currentTime, questType = QuestType.WEEKLY),
            Quest("Study", "You must study because you will fail", false, currentTime, questType = QuestType.WEEKLY),
            Quest("Work", "You must work because you will be poor", false, currentTime, questType = QuestType.WEEKLY),
            Quest("Say Hi To Girlfriend", "You must say hi to girlfriend because you will be punished", false, currentTime, questType = QuestType.WEEKLY),
            Quest("Work out", "You must work out because you will be weak", false, currentTime, questType = QuestType.WEEKLY),
            Quest("Clean Shit", "You must clean shit because you will be punished", false, currentTime, questType = QuestType.MONTHLY),
            Quest("Take Shit", "You must take shit because you will explode", false, currentTime, questType = QuestType.MONTHLY),
            Quest("Feed Cat", "You must feed cat because cat will die... witch is not so bad..", false, currentTime, questType = QuestType.MONTHLY),
            Quest("Study", "You must study because you will fail", false, currentTime, questType = QuestType.MONTHLY),
            Quest("Work", "You must work because you will be poor", false, currentTime, questType = QuestType.MONTHLY),
            Quest("Say Hi To Girlfriend", "You must say hi to girlfriend because you will be punished", false, currentTime, questType = QuestType.MONTHLY),
            Quest("Work out", "You must work out because you will be weak", false, currentTime, questType = QuestType.MONTHLY),
        )
        return q[Random.nextInt(0, q.size-1)]
    }
}