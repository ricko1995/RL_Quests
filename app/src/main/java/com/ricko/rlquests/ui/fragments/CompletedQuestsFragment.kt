package com.ricko.rlquests.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.ricko.rlquests.R
import com.ricko.rlquests.adapters.QuestsRecyclerAdapter
import com.ricko.rlquests.databinding.FragmentCompletedQuestsBinding
import com.ricko.rlquests.db.QuestType
import com.ricko.rlquests.ui.MainActivity
import com.ricko.rlquests.ui.viewmodels.CompletedQuestsViewModel
import com.ricko.rlquests.util.DateConversion
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_completed_quests.*

@AndroidEntryPoint
class CompletedQuestsFragment : Fragment(R.layout.fragment_completed_quests) {

    private val viewModel: CompletedQuestsViewModel by activityViewModels()
    private lateinit var recyclerAdapter: QuestsRecyclerAdapter

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().navigate(CompletedQuestsFragmentDirections.actionCompletedQuestsFragmentToQuestsFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainActivity.supportActionBar?.title = "Completed Quests"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentCompletedQuestsBinding.inflate(inflater, container, false)
        binding.apply {
            completedQuestsViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter = QuestsRecyclerAdapter()
        setupRecyclerView()
        registerObservers()

        calendarView.setOnDateChangedListener { widget, date, selected ->
            widget.selectedDate = null
            viewModel.lastSelectedDate = date
            updateCalendarView(date)
        }


//        calendarView.setWeekDayLabels(arrayOf("PON", "UTO", "SRI", "ČET", "PET", "SUB", "NED"))
//        calendarView.setTitleMonths(arrayOf("Siječanj", "Veljača", "Ožujak", "Travanj", "Svibanj", "Lipanj", "Srpanj", "Kolovoz", "Rujan", "Listopad", "Studeni", "Prosinac"))


    }

    private fun updateCalendarView(date: CalendarDay) {
        val days = mutableListOf<CalendarDay>()
        var millis = Pair(0L, Long.MAX_VALUE)

        when (viewModel.questType.value) {
            QuestType.DAILY -> {
                days.add(date)
                millis = DateConversion.getOneDayMillis(date)
            }
            QuestType.WEEKLY -> {
                days.addAll(DateConversion.getWeekDays(date))
                millis = DateConversion.getMillisFromListOfDates(days)
            }
            QuestType.MONTHLY -> {
                days.addAll(DateConversion.getMonthDays(date))
                millis = DateConversion.getMillisFromListOfDates(days)
            }
        }

        viewModel.setupFirstLastDay(millis)

        for (day in days) {
            calendarView.setDateSelected(day, true)
        }
        days.clear()
    }

    private fun setupRecyclerView() = rvCompletedQuests.apply {
        adapter = recyclerAdapter
        layoutManager = LinearLayoutManager(activity)
    }

    private fun registerObservers() {
        when (viewModel.questType.value) {
            QuestType.DAILY -> {
                btnDailyToggle.performClick()
            }
            QuestType.WEEKLY -> {
                btnWeeklyToggle.performClick()
            }
            QuestType.MONTHLY -> {
                btnMonthlyToggle.performClick()
            }
            else -> {
                btnDailyToggle.performClick()
            }
        }
        viewModel.questType.observe(viewLifecycleOwner) {
            when (it) {
                QuestType.DAILY -> {
                    calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_SINGLE
                }
                QuestType.WEEKLY -> {
                    calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_MULTIPLE
                }
                QuestType.MONTHLY -> {
                    calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_MULTIPLE
                }
                else -> {
                    calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_SINGLE
                }
            }
            calendarView.selectedDate = viewModel.lastSelectedDate
            updateCalendarView(viewModel.lastSelectedDate)
        }

        viewModel.quests.observe(viewLifecycleOwner) {
            recyclerAdapter.submitQuestList(it)

        }
    }
}