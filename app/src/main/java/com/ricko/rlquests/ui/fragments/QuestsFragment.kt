package com.ricko.rlquests.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ricko.rlquests.R
import com.ricko.rlquests.adapters.QuestsRecyclerAdapter
import com.ricko.rlquests.db.Quest
import com.ricko.rlquests.db.QuestType
import com.ricko.rlquests.ui.MainActivity
import com.ricko.rlquests.ui.viewmodels.QuestsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quests.*

@AndroidEntryPoint
class QuestsFragment : Fragment(R.layout.fragment_quests), QuestsRecyclerAdapter.OnQuestItemLongClickListener {

    lateinit var questsRecyclerAdapter: QuestsRecyclerAdapter
    private val viewModel: QuestsViewModel by activityViewModels()
    private val args by navArgs<QuestsFragmentArgs>()

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        questsRecyclerAdapter = QuestsRecyclerAdapter(this)

        setupRecyclerView()
        registerObservers()
        insertNewOrUpdatedQuest()



        fabAddQuest.setOnClickListener {
            findNavController().navigate(QuestsFragmentDirections.actionQuestsFragmentToCreateNewQuestFragment())
        }
    }

    private fun insertNewOrUpdatedQuest() {
        args.quest?.let {
            viewModel.insertQuest(it)
        }
    }

    private fun registerObservers() {
        viewModel.quests.observe(viewLifecycleOwner) {
            questsRecyclerAdapter.submitQuestList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.quest_type_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val result = super.onPrepareOptionsMenu(menu)
        val dailyMenuItem = menu.findItem(R.id.menuDaily)
        val weeklyMenuItem = menu.findItem(R.id.menuWeekly)
        val monthlyMenuItem = menu.findItem(R.id.menuMonthly)
        val mainActivity = activity as MainActivity
        when (viewModel.questType) {
            QuestType.DAILY -> {
                mainActivity.supportActionBar?.title = "Daily Quests"
                dailyMenuItem.setIcon(R.drawable.ic_error_filled)
                weeklyMenuItem.setIcon(R.drawable.ic_today_outline)
                monthlyMenuItem.setIcon(R.drawable.ic_calendar_outline)
            }
            QuestType.WEEKLY -> {
                mainActivity.supportActionBar?.title = "Weekly Quests"
                dailyMenuItem.setIcon(R.drawable.ic_error_outline)
                weeklyMenuItem.setIcon(R.drawable.ic_today_filled)
                monthlyMenuItem.setIcon(R.drawable.ic_calendar_outline)
            }
            QuestType.MONTHLY -> {
                mainActivity.supportActionBar?.title = "Monthly Quests"
                dailyMenuItem.setIcon(R.drawable.ic_error_outline)
                weeklyMenuItem.setIcon(R.drawable.ic_today_outline)
                monthlyMenuItem.setIcon(R.drawable.ic_calendar_filled)
            }
        }
        return result
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuDaily -> {
                viewModel.setQuestType(QuestType.DAILY)
                fabAddQuest.animate().scaleX(1f).scaleY(1f).duration = 150
                requireActivity().invalidateOptionsMenu()
            }
            R.id.menuWeekly -> {
                viewModel.setQuestType(QuestType.WEEKLY)
                fabAddQuest.animate().scaleX(1f).scaleY(1f).duration = 150
                requireActivity().invalidateOptionsMenu()
            }
            R.id.menuMonthly -> {
                viewModel.setQuestType(QuestType.MONTHLY)
                fabAddQuest.animate().scaleX(1f).scaleY(1f).duration = 150
                requireActivity().invalidateOptionsMenu()
            }
            R.id.menuAllCompletedQuests -> {
                findNavController().navigate(QuestsFragmentDirections.actionQuestsFragmentToCompletedQuestsFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() = rvQuests.apply {
        adapter = questsRecyclerAdapter
        layoutManager = LinearLayoutManager(activity)
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(this)
        var isAnimating = false
        setOnScrollChangeListener { _, _, _, _, direction ->
            if (isAnimating) return@setOnScrollChangeListener
            isAnimating = true
            when {
                direction > 0 -> fabAddQuest.animate().scaleX(1f).scaleY(1f).withEndAction {
                    isAnimating = false
                }.duration = 150
                direction < 0 -> fabAddQuest.animate().scaleX(0f).scaleY(0f).withEndAction {
                    isAnimating = false
                }.duration = 150
                else -> isAnimating = false
            }
        }
    }

    private val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
        ACTION_STATE_IDLE, LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val quest = questsRecyclerAdapter.differ.currentList[position]
            when (direction) {
                LEFT -> {
                    viewModel.deleteQuest(quest)
                    Snackbar.make(requireView(), "Successfully deleted quest", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo") {
                            viewModel.insertQuest(quest)
                        }
                        show()
                    }
                }
                RIGHT -> {
                    quest.isCompleted = true
                    quest.completionDate = System.currentTimeMillis()
                    quest.setIcon()
                    viewModel.insertQuest(quest)
                    viewHolder.itemView.animate().apply {
                        duration = 80
                        translationX(0f)
                        withEndAction {
                            questsRecyclerAdapter.notifyItemChanged(position)
                        }
                    }.start()
                }

            }

        }

    }

    override fun questItemLongClick(quest: Quest) {
        findNavController().navigate(QuestsFragmentDirections.actionQuestsFragmentToCreateNewQuestFragment(quest))
    }
}