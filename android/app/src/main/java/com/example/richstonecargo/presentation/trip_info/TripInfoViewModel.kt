package com.example.richstonecargo.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.data.remote.dto.ButtonState
import com.example.richstonecargo.data.remote.dto.ProductDetails
import com.example.richstonecargo.data.remote.dto.TripDetails

class TripInfoViewModel : ViewModel() {
    private val _trip = MutableLiveData<TripDetails>()
    val trip: LiveData<TripDetails> = _trip

    private val _product = MutableLiveData<ProductDetails>()
    val product: LiveData<ProductDetails> = _product

    private val _buttonState = MutableLiveData<ButtonState>()
    val buttonState: LiveData<ButtonState> = _buttonState

    init {
        loadTripDetails()
        loadProductDetails()
        _buttonState.value = ButtonState("Начать рейс", true)
    }

    private fun loadTripDetails() {
        _trip.value = TripDetails(
            tripNumber = "CU-425",
            loadPoint = "Хоргос",
            unloadPoint = "Алматы",
            loadDate = "01.03.2024",
            loadTime = "09:00",
            distance = "331 км",
            tripTime = "3ч. 37м."
        )
    }

    private fun loadProductDetails() {
        _product.value = ProductDetails(
            name = "XXXXXX",
            description = "XXXXXX",
            totalWeight = "XXXXXX",
            palletCount = "XXXXXX",
            temperature = "XXXXXX"
        )
    }

    fun onButtonClick(navController: NavController) {
        navController.navigate("start_trip_screen")
        _buttonState.value = _buttonState.value?.copy(isEnabled = false)
    }

    fun onBuildRouteClick() {
        // Add logic to handle route building
        // For instance, open a map view or call an API to calculate route
    }

    fun onEndTripClick(navController: NavController) {
        // Logic to end the trip
        navController.navigate("trip_info_screen")
    }
}
