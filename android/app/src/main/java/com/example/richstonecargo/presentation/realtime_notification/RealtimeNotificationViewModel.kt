package com.example.richstonecargo.presentation.realtime_notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RealtimeNotificationViewModel : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun newMessageReceived(newMessage: String) {
        _message.value = newMessage
    }
}
