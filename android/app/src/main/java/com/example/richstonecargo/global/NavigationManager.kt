package com.example.richstonecargo.global

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NavigationManager {
    private val _commands = MutableLiveData<Event<NavigationCommand>>()
    val commands: LiveData<Event<NavigationCommand>> get() = _commands

    fun navigate(command: NavigationCommand) {
        _commands.postValue(Event(command))
    }
}

sealed class NavigationCommand {
    data class ToDestination(val route: String) : NavigationCommand()
    object Back : NavigationCommand()
}