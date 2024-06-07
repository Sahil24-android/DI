package com.event.eventmanagement.repository

import com.event.eventmanagement.apis.LocationService
import com.event.eventmanagement.apis.Result
import com.event.eventmanagement.views.auth.datasource.PinCodeData
import javax.inject.Inject

class LocationRepository @Inject constructor(private val locationService: LocationService) {

    suspend fun getLocationData(pinCode: String): Result<ArrayList<PinCodeData>> {
        return try {
            val response = locationService.getDataFromPinCode(pinCode)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }
}