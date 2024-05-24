package com.event.eventmanagement.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.event.eventmanagement.apis.Result
import com.event.eventmanagement.repository.UserRepository
import com.event.eventmanagement.views.activity.addeventPayments.PaymentBody
import com.event.eventmanagement.views.activity.addeventPayments.PaymentResponse
import com.event.eventmanagement.views.activity.createCustomerEvent.EventBodyRequest
import com.event.eventmanagement.views.activity.createCustomerEvent.NewEventResponse
import com.event.eventmanagement.views.activity.customerEventList.data.GetCustomerEventDataList
import com.event.eventmanagement.views.activity.exposed.data.ExposedBody
import com.event.eventmanagement.views.activity.exposed.data.ExposedResponse
import com.event.eventmanagement.views.activity.exposed.data.VendorListResponse
import com.event.eventmanagement.views.activity.invoice.data.PdfBody
import com.event.eventmanagement.views.auth.datasource.LoginBody
import com.event.eventmanagement.views.auth.datasource.LoginResponse
import com.event.eventmanagement.views.auth.datasource.PackageData
import com.event.eventmanagement.views.auth.datasource.PinCodeData
import com.event.eventmanagement.views.auth.datasource.RegisterUserResponse
import com.event.eventmanagement.views.auth.datasource.RegistrationDataSend
import com.event.eventmanagement.views.auth.datasource.ServicesData
import com.event.eventmanagement.views.fragment.datasource.AllEventDates
import com.event.eventmanagement.views.fragment.datasource.AllEventsResponse
import com.event.eventmanagement.views.fragment.datasource.AllPackageResponse
import com.event.eventmanagement.views.fragment.datasource.CustomerResponse
import com.event.eventmanagement.views.fragment.datasource.EventBody
import com.event.eventmanagement.views.fragment.datasource.EventResponse
import com.event.eventmanagement.views.fragment.datasource.PackageBody
import com.event.eventmanagement.views.fragment.datasource.PackageResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.ResponseBody

class UserViewModel : ViewModel() {

    private var repository: UserRepository = UserRepository()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> get() = _isLoading


    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> get() = _error


    private val _serviceName: MutableLiveData<ServicesData> = MutableLiveData()
    val serviceName: LiveData<ServicesData> get() = _serviceName


    fun getServices() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getServices()
            if (result is Result.Success) {
                _serviceName.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false

            }
            _isLoading.value = false
        }
    }


    private val _packages: MutableLiveData<PackageData> = MutableLiveData()
    val servicePackages: LiveData<PackageData> get() = _packages


    fun getServicePackage(serviceId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getPackages(serviceId)
            if (result is Result.Success) {
                _packages.value = result.value
                _isLoading.value = false

            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false

            }
            _isLoading.value = false
        }
    }


    private val _locations: MutableLiveData<ArrayList<PinCodeData>> = MutableLiveData()
    val locationData: LiveData<ArrayList<PinCodeData>> get() = _locations

    fun getLocationsData(pincode: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getLocationData(pincode)
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

    private val _registerUser: MutableLiveData<RegisterUserResponse> = MutableLiveData()
    val userRegister: LiveData<RegisterUserResponse> get() = _registerUser

    fun registerUser(data: RegistrationDataSend, multipartFormData: MultipartBody.Part) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.registerUser(data, multipartFormData)
            if (result is Result.Success) {
                _registerUser.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
            }
            _isLoading.value = false
        }
    }

    private val _addEvent: MutableLiveData<EventResponse> = MutableLiveData()
    val eventResponse: LiveData<EventResponse> get() = _addEvent
    fun addEvent(eventBody: EventBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.addEvent(eventBody)
            if (result is Result.Success) {
                _addEvent.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }


    private val _addPackage: MutableLiveData<PackageResponse> = MutableLiveData()
    val packageResponse: LiveData<PackageResponse> get() = _addPackage
    fun createPackage(packageBody: PackageBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.createPackage(packageBody)
            if (result is Result.Success) {
                _addPackage.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

    private val _getAllEvent: MutableLiveData<AllEventsResponse> = MutableLiveData()
    val getAllEventsResponse: LiveData<AllEventsResponse> get() = _getAllEvent
    fun getEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getEvents()
            if (result is Result.Success) {
                _getAllEvent.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
            }
            _isLoading.value = false
        }
    }


    private val _getEventPackages: MutableLiveData<AllPackageResponse> = MutableLiveData()
    val getEventPackages: LiveData<AllPackageResponse> get() = _getEventPackages
    fun getEventPackages() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getPackages()
            if (result is Result.Success) {
                _getEventPackages.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
            }
            _isLoading.value = false
        }
    }

    private val _newEvent: MutableLiveData<NewEventResponse> = MutableLiveData()
    val newEventResponse: LiveData<NewEventResponse> get() = _newEvent
    fun addNewEvent(eventBody: EventBodyRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.addNewEvent(eventBody)
            if (result is Result.Success) {
                _newEvent.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

    private val _getAllCustomerEvents: MutableLiveData<GetCustomerEventDataList> = MutableLiveData()
    val getAllCustomerEvents: LiveData<GetCustomerEventDataList> get() = _getAllCustomerEvents
    fun getAllEvents(vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getAllEvents(vendorId)
            if (result is Result.Success) {
                _getAllCustomerEvents.value = result.value
            } else if (result is Result.Error) {
                _error.value = result.message
            }
            _isLoading.value = false
        }
    }


    private val _allEventsDates: MutableLiveData<AllEventDates> = MutableLiveData()
    val allEventsDates: LiveData<AllEventDates> get() = _allEventsDates

    fun getAllEventDates(vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.allEventDates(vendorId)
            if (result is Result.Success) {
                _allEventsDates.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }


    private val _getEventByDate: MutableLiveData<GetCustomerEventDataList> = MutableLiveData()
    val getEventByDate: LiveData<GetCustomerEventDataList> get() = _getEventByDate
    fun getEventByDate(date: String, vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getEventByDate(date, vendorId)
            if (result is Result.Success) {
                _getEventByDate.value = result.value
            } else if (result is Result.Error) {
                _error.value = result.message
            }
            _isLoading.value = false
        }
    }

    fun getEventByCustomer(customer_id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getEventByCustomer(customer_id)
            if (result is Result.Success) {
                _getEventByDate.value = result.value
            } else if (result is Result.Error) {
                _error.value = result.message
            }
            _isLoading.value = false
        }
    }


    private val _addPayment: MutableLiveData<PaymentResponse> = MutableLiveData()
    val addPayment: LiveData<PaymentResponse> get() = _addPayment
    fun addPayment(paymentBody: PaymentBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.addPayment(paymentBody)
            if (result is Result.Success) {
                _addPayment.value = result.value
            } else if (result is Result.Error) {
                _error.value = result.message
            }
            _isLoading.value = false
        }
    }


    private val _getCustomerResponse: MutableLiveData<CustomerResponse> = MutableLiveData()
    val getCustomerResponse: LiveData<CustomerResponse> get() = _getCustomerResponse
    fun getAllCustomers() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getAllCustomers()
            if (result is Result.Success) {
                _getCustomerResponse.value = result.value
            } else if (result is Result.Error) {
                _error.value = result.message

            }
            _isLoading.value = false
        }
    }

    private val _loginResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    val loginResponse: LiveData<LoginResponse> get() = _loginResponse
    fun login(loginBody: LoginBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.login(loginBody)
            if (result is Result.Success) {
                _loginResponse.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
            }
            _isLoading.value = false
        }
    }


    private val _paymentUrlResponse: MutableLiveData<ResponseBody> = MutableLiveData()
    val paymentUrlResponse: LiveData<ResponseBody> get() = _paymentUrlResponse


    fun updatePdfUrl(pDfBody: PdfBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.updatePdfUrl(pDfBody)
            if (result is Result.Success) {
                _paymentUrlResponse.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

    private val _vendorList: MutableLiveData<VendorListResponse> = MutableLiveData()
    val vendorList: LiveData<VendorListResponse> get() = _vendorList

    fun getAllVendor() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getAllVendors()
            if (result is Result.Success) {
                _vendorList.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

    private val _eventTransferResponse: MutableLiveData<ExposedResponse> = MutableLiveData()
    val eventTransferResponse: MutableLiveData<ExposedResponse> get() = _eventTransferResponse


    fun transferEvent(exposedBody: ExposedBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.transferEvent(exposedBody)
            if (result is Result.Success) {
                _eventTransferResponse.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

    private val _eventExposedByMe: MutableLiveData<GetCustomerEventDataList> = MutableLiveData()
    val eventExposedByMe: MutableLiveData<GetCustomerEventDataList> get() = _eventExposedByMe

    fun eventExposedByMe(vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = false
            val result = repository.eventExposedByMe(vendorId)
            if (result is Result.Success) {
                _eventExposedByMe.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

    private val _eventExposedToMe: MutableLiveData<GetCustomerEventDataList> = MutableLiveData()
    val eventExposedToMe: MutableLiveData<GetCustomerEventDataList> get() = _eventExposedToMe

    fun eventExposedToMe(vendorId: String){
        viewModelScope.launch {
            _isLoading.value = false
            val result = repository.eventExposedToMe(vendorId)
            if (result is Result.Success) {
                _eventExposedToMe.value = result.value
                _isLoading.value = false
            }else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
            }
    }
}