package com.ricko.rlquests.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import com.ricko.rlquests.R
import com.ricko.rlquests.db.QuestDao
import com.ricko.rlquests.other.TestQuests.listOfFakeQuests
import com.ricko.rlquests.repositories.MainRepository
import com.ricko.rlquests.ui.viewmodels.QuestsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}