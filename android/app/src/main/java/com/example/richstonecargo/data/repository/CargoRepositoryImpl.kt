package com.example.richstonecargo.data.repository


//class CargoRepositoryImpl @Inject constructor(
//    private val api: CargoBackendApi
//) : CargoRepository {
//    override suspend fun loginUser(phoneNumber: String, password: String): UserInfo {
//        return UserInfo(1, "Rufina")
//    }
//
//    override suspend fun getTripList(): List<Trip> {
//        return listOf(createExampleTrip())
//    }
//
//    private fun createExampleTrip(): Trip {
//        // Generate some random ID for the trip, typically this would be done by your database
//        val tripId = List(10) { (('A'..'Z') + ('0'..'9')).random() }.joinToString("")
//
//        val cargo = CargoInfo(
//            id = "CRG-1",
//            name = "Electronics",
//            description = "Consumer electronics",
//            totalWeight = 1500.0, // Weight in kg
//            palletCount = 10,
//            temperatureMode = "Ambient" // Just an example, could be "Refrigerated" etc.
//        )
//
//        val departureDate = Date() // Current date and time
//        val arrivalDate = Date(departureDate.time + 1000 * 60 * 60 * 24) // Plus one day for example
//
//        return Trip(
//            id = tripId,
//            tripNumber = "KZ-ALM-KHR-001",
//            departurePlace = "Khorgos",
//            arrivalPlace = "Almaty",
//            departureDate = departureDate,
//            arrivalDate = arrivalDate,
//            cargoInfo = cargo
//        )
//    }
//}