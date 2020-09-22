package com.ricko.rlquests.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ricko.rlquests.R
import com.ricko.rlquests.databinding.FragmentCreateNewQuestBinding
import com.ricko.rlquests.db.Quest
import com.ricko.rlquests.db.QuestType
import com.ricko.rlquests.ui.MainActivity
import com.ricko.rlquests.ui.viewmodels.CreateQuestViewModel
import com.ricko.rlquests.util.DateConversion
import com.ricko.rlquests.util.ClickHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_new_quest.*

@AndroidEntryPoint
class CreateNewQuestFragment : Fragment(R.layout.fragment_create_new_quest) {

    private val args by navArgs<CreateNewQuestFragmentArgs>()
    private var questByArgs: Quest? = null
    private val viewModel: CreateQuestViewModel by viewModels()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().navigate(CreateNewQuestFragmentDirections.actionCreateNewQuestFragmentToQuestsFragment())
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainActivity.supportActionBar?.title = if (args.quest != null) "Edit Quest" else "Create New Quest"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentCreateNewQuestBinding.inflate(inflater, container, false)
        binding.apply {
            handler = ClickHandler()
            createQuestViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        args.quest?.let {
            questByArgs = it
            when (it.questType) {
                QuestType.DAILY -> viewModel.isDailyRadioChecked.value = true
                QuestType.WEEKLY -> viewModel.isWeeklyRadioChecked.value = true
                QuestType.MONTHLY -> viewModel.isMonthlyRadioChecked.value = true
            }
            viewModel.questTitle.value = it.title
            viewModel.questDescription.value = it.description
            viewModel.createdDate.value = DateConversion.getCleanCreatedDate(it)
            viewModel.isEditing.value = false
        }

        btnSave.setOnClickListener {
            ClickHandler().hideKeyboard(it)
            if (questByArgs == null) createQuest()
            else updateQuest()
        }
    }

    private fun updateQuest() {
        val isTitleAndDescriptionValid = !viewModel.questTitle.value.isNullOrEmpty() &&
                !viewModel.questDescription.value.isNullOrEmpty()
        val isRadioButtonCheckedValid =
            viewModel.isDailyRadioChecked.value!!.xor(viewModel.isWeeklyRadioChecked.value!!.xor(viewModel.isMonthlyRadioChecked.value!!))
        if (isTitleAndDescriptionValid && isRadioButtonCheckedValid) {
            questByArgs!!.title = viewModel.questTitle.value!!
            questByArgs!!.description = viewModel.questDescription.value!!
            questByArgs!!.questType = when {
                viewModel.isDailyRadioChecked.value == true -> QuestType.DAILY
                viewModel.isWeeklyRadioChecked.value == true -> QuestType.WEEKLY
                viewModel.isMonthlyRadioChecked.value == true -> QuestType.MONTHLY
                else -> QuestType.DAILY
            }
            findNavController().navigate(CreateNewQuestFragmentDirections.actionCreateNewQuestFragmentToQuestsFragment(questByArgs))
        } else {
            Toast.makeText(requireContext(), "Enter valid data", Toast.LENGTH_SHORT).show()
        }

    }

    private fun createQuest() {
        val isTitleAndDescriptionValid = !viewModel.questTitle.value.isNullOrEmpty() &&
                !viewModel.questDescription.value.isNullOrEmpty()
        val isRadioButtonCheckedValid =
            viewModel.isDailyRadioChecked.value!!.xor(viewModel.isWeeklyRadioChecked.value!!.xor(viewModel.isMonthlyRadioChecked.value!!))

        if (isTitleAndDescriptionValid && isRadioButtonCheckedValid) {
            val title = viewModel.questTitle.value!!
            val description = viewModel.questDescription.value!!
            val questType = when {
                viewModel.isDailyRadioChecked.value == true -> QuestType.DAILY
                viewModel.isWeeklyRadioChecked.value == true -> QuestType.WEEKLY
                viewModel.isMonthlyRadioChecked.value == true -> QuestType.MONTHLY
                else -> QuestType.DAILY
            }
            val creationDate = System.currentTimeMillis()
            val newQuest = Quest(
                title = title,
                description = description,
                questType = questType,
                creationDate = creationDate
            )
            println(newQuest)
            findNavController().navigate(CreateNewQuestFragmentDirections.actionCreateNewQuestFragmentToQuestsFragment(newQuest))
        } else {
            Toast.makeText(requireContext(), "Enter valid data", Toast.LENGTH_SHORT).show()
        }
    }

}