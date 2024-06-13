package com.example.richstonecargo.presentation.calculation_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.domain.model.SalaryInfo
import com.example.richstonecargo.domain.repository.CargoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculationListViewModel @Inject constructor(
    private val repository: CargoRepository
) : ViewModel() {
    private val _salaryInfo = MutableStateFlow<SalaryInfo?>(null)
    val salaryInfo: StateFlow<SalaryInfo?> = _salaryInfo

    private val _availableMonths = MutableStateFlow<List<String>>(emptyList())
    val availableMonths: StateFlow<List<String>> = _availableMonths

    init {
        fetchAvailableMonths()
    }

    fun fetchSalaryInfo(yearMonth: String) {
        viewModelScope.launch {
            try {
                val result = repository.getSalaryInfo(yearMonth)
                _salaryInfo.value = result
            } catch (e: Exception) {
                Log.d("fetchSalaryInfo", "Error fetching salary info: ${e.message}")
            }
        }
    }

    private fun fetchAvailableMonths() {
        viewModelScope.launch {
            try {
                val result = repository.getAvailableMonths()
                _availableMonths.value = result
                if (result.isNotEmpty()) {
                    val salaryInfo = repository.getSalaryInfo(result[0])
                    _salaryInfo.value = salaryInfo
                }
            } catch (e: Exception) {
                println("Error fetching available months: ${e.message}")
            }
        }
    }
}
