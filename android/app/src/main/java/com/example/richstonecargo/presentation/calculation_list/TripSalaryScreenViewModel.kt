package com.example.richstonecargo.presentation.trip_salary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.domain.model.PayslipDetails
import com.example.richstonecargo.domain.repository.CargoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class TripSalaryScreenViewModel @Inject constructor(
    private val repository: CargoRepository
) : ViewModel() {

    private val _payslipDetails = MutableStateFlow<List<PayslipDetails>>(emptyList())
    val payslipDetails: StateFlow<List<PayslipDetails>> = _payslipDetails

    fun fetchPayslipDetails(yearMonth: String) {
        viewModelScope.launch {
            try {
                val result = repository.getPayslipDetails(yearMonth)
                _payslipDetails.value = result
            } catch (e: HttpException) {
                Log.e("fetchPayslipDetails", "HTTP exception: ${e.message}")
            } catch (e: Exception) {
                Log.e("fetchPayslipDetails", "Exception: ${e.message}")
            }
        }
    }
}
