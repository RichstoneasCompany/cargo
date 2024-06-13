package com.example.richstonecargo.presentation.notification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.domain.model.TripChange
import com.example.richstonecargo.domain.repository.CargoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TripChangeState(
    val isLoading: Boolean = false,
    val changes: List<TripChange>? = emptyList(),
    val error: String = ""
)

@HiltViewModel
class TripChangesViewModel @Inject constructor(
    private val repository: CargoRepository
) : ViewModel() {

    private val _tripChanges = MutableStateFlow<List<TripChange>>(emptyList())
    val tripChanges: StateFlow<List<TripChange>> = _tripChanges

    init {
        getTripChanges()
    }

    fun getTripChanges() {
        viewModelScope.launch {
            try {
                val result = repository.getTripChanges()
                _tripChanges.value = result
            } catch (e: Exception) {
                Log.d("getTripChanges", "Error fetching getTripChanges: ${e.message}")
            }
        }
    }
}
