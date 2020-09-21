package com.ricko.rlquests.ui.viewmodels

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateQuestViewModel : ViewModel(), Observable {

    @Bindable
    val questTitle: MutableLiveData<String> = MutableLiveData("")

    @Bindable
    val questDescription: MutableLiveData<String> = MutableLiveData("")

    @Bindable
    val isEditing: MutableLiveData<Boolean> = MutableLiveData(true)

    @Bindable
    val isDailyRadioChecked: MutableLiveData<Boolean> = MutableLiveData(false)

    @Bindable
    val isWeeklyRadioChecked: MutableLiveData<Boolean> = MutableLiveData(false)

    @Bindable
    val isMonthlyRadioChecked: MutableLiveData<Boolean> = MutableLiveData(false)

    val createdDate: MutableLiveData<String> = MutableLiveData("")


    //Unused
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    //Unused
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}