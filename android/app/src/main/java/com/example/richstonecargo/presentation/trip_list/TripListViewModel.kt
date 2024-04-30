package com.example.richstonecargo.presentation.trip_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.data.remote.dto.PayslipDto
import com.example.richstonecargo.data.remote.dto.TripData

class TripListViewModel : ViewModel() {
    private val _trips = MutableLiveData<List<TripData>>()
    val trips: LiveData<List<TripData>> = _trips

    init {
        loadTrips()
    }

    private fun loadTrips() {
        // Dummy data loading, replace with actual data fetching logic
        _trips.value = listOf(
            TripData(date = "01", year = "2024", month = "МАРТ", tripNumber = "CU-425", route = "Алматы-Хоргос", loadingTime = "09:00", tripTime = "3ч. 37м.", isCurrentCargo = false),
            TripData(date = "02", year = "2024", month = "МАРТ", tripNumber = "CU-426", route = "Нур-Султан-Хоргос", loadingTime = "10:00", tripTime = "4ч. 00м.", isCurrentCargo = true),
            // Add more trips as necessary
        )
    }

    fun onTripSelected(navController: NavController, trip: TripData) {
        // Handle trip selection
        navController.navigate("trip_detail_screen/${trip.tripNumber}")  // Assuming dynamic navigation
    }

    // Example function to generate payslip for a driver
    fun generatePayslipForDriver(driverId: Long, periodStart: String, periodEnd: String): PayslipDto {
        // Simulate payslip generation
        return PayslipDto(driverId, periodStart, periodEnd)
    }
}
