package com.ricko.rlquests.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ricko.rlquests.R
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "quests_table")
data class Quest(
    var title: String = "",
    var description: String = "",
    var isCompleted: Boolean = false,
    val creationDate: Long = 0L,
    var completionDate: Long? = null,
    var questType: QuestType,
    @PrimaryKey(autoGenerate = false)
    var id: String = UUID.randomUUID().toString()
) : Parcelable{

    @IgnoredOnParcel
    var questIcon = if (isCompleted) R.drawable.ic_check_circle_animated else R.drawable.ic_clock
    fun setIcon(){
        questIcon = if (isCompleted) R.drawable.ic_check_circle_animated else R.drawable.ic_clock
    }
}

enum class QuestType {
    DAILY, WEEKLY, MONTHLY
}