package com.example.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel: ViewModel() {

    private val _timer = MutableLiveData<Int>()
    val timer: LiveData<Int> get() = _timer

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            for(i in 1..60) {
                _timer.postValue(i)
                delay(1000)
            }
        }
    }

}