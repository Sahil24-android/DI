package com.event.eventmanagement.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.event.eventmanagement.apis.Result
import com.event.eventmanagement.repository.LocationRepository
import com.event.eventmanagement.views.auth.datasource.PinCodeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val locationRepository: LocationRepository):ViewModel() {

    private val _locations: MutableLiveData<ArrayList<PinCodeData>> = MutableLiveData()
    val locationData: LiveData<ArrayList<PinCodeData>> get() = _locations

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> get() = _isLoading


    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> get() = _error
    fun getLocationsData(pincode: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = locationRepository.getLocationData(pincode)
            if (result is Result.Success) {
                _locations.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

}