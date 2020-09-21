package com.ricko.rlquests.adapters

import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ricko.rlquests.R
import com.ricko.rlquests.db.Quest
import com.ricko.rlquests.util.DateConversion
import kotlinx.android.synthetic.main.quest_item.view.*

class QuestsRecyclerAdapter(private val questItemListener: OnQuestItemLongClickListener) : RecyclerView.Adapter<QuestsRecyclerAdapter.QuestViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Quest>() {
        override fun areItemsTheSame(oldItem: Quest, newItem: Quest): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quest, newItem: Quest): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffCallback)

    fun submitQuestList(list: List<Quest>) = differ.submitList(list)

    inner class QuestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestViewHolder {
        return QuestViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quest_item, parent, false))
    }

    override fun onBindViewHolder(holder: QuestViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.itemView.apply {
            tvTitle.text = currentItem.title
            tvDescription.text = currentItem.description
            tvDate.text = DateConversion.getCleanElapsedTime(currentItem)
//            ivQuestIcon.setImageResource(currentItem.questIcon)
            Glide.with(this).load(currentItem.questIcon).into(ivQuestIcon)
            setOnLongClickListener {
                performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
                )
                questItemListener.questItemLongClick(currentItem)
                return@setOnLongClickListener true
            }
        }
    }

    interface OnQuestItemLongClickListener {
        fun questItemLongClick(quest: Quest)
    }

    override fun getItemCount() = differ.currentList.size
}