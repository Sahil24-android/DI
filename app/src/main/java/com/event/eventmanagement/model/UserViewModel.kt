package com.event.eventmanagement.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.event.eventmanagement.apis.Result
import com.event.eventmanagement.repository.UserRepository
import com.event.eventmanagement.views.activity.addEmployee.AddEmployeeResponse
import com.event.eventmanagement.views.activity.addEmployee.EmployeeBody
import com.event.eventmanagement.views.activity.addEmployee.GetEmployeeResponse
import com.event.eventmanagement.views.activity.addeventPayments.PaymentBody
import com.event.eventmanagement.views.activity.addeventPayments.PaymentResponse
import com.event.eventmanagement.views.activity.createCustomerEvent.EventBodyRequest
import com.event.eventmanagement.views.activity.createCustomerEvent.NewEventResponse
import com.event.eventmanagement.views.activity.customerEventList.data.GetCustomerEventDataList
import com.event.eventmanagement.views.activity.employeeExpense.data.EmployeeExpenseResponse
import com.event.eventmanagement.views.activity.exposed.data.ExposedBody
import com.event.eventmanagement.views.activity.exposed.data.ExposedResponse
import com.event.eventmanagement.views.activity.exposed.data.VendorListResponse
import com.event.eventmanagement.views.activity.gallery.ImageResponse
import com.event.eventmanagement.views.activity.invoice.data.PdfBody
import com.event.eventmanagement.views.activity.profile.data.BuyPackageBody
import com.event.eventmanagement.views.activity.profile.data.MyPackageResponse
import com.event.eventmanagement.views.activity.report.data.FromToDateBody
import com.event.eventmanagement.views.activity.vendorExpense.data.EmployeeExpenseBody
import com.event.eventmanagement.views.activity.vendorExpense.data.VendorExpenseBody
import com.event.eventmanagement.views.activity.vendorExpense.data.VendorExpenseResponse
import com.event.eventmanagement.views.auth.datasource.LoginBody
import com.event.eventmanagement.views.auth.datasource.LoginResponse
import com.event.eventmanagement.views.auth.datasource.PackageData
import com.event.eventmanagement.views.auth.datasource.RegisterUserResponse
import com.event.eventmanagement.views.auth.datasource.RegistrationDataSend
import com.event.eventmanagement.views.auth.datasource.ServicesData
import com.event.eventmanagement.views.fragment.datasource.AllEventDates
import com.event.eventmanagement.views.fragment.datasource.AllEventsResponse
import com.event.eventmanagement.views.fragment.datasource.AllPackageResponse
import com.event.eventmanagement.views.fragment.datasource.CustomerResponse
import com.event.eventmanagement.views.fragment.datasource.EventBody
import com.event.eventmanagement.views.fragment.datasource.EventResponse
import com.event.eventmanagement.views.fragment.datasource.GalleryResponse
import com.event.eventmanagement.views.fragment.datasource.GetReportResponse
import com.event.eventmanagement.views.fragment.datasource.GetVendorExpenseResponse
import com.event.eventmanagement.views.fragment.datasource.PackageBody
import com.event.eventmanagement.views.fragment.datasource.PackageResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {


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


    fun getServicePackage(token: String,serviceId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getServicesVendor(token,serviceId)
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
    fun addEvent(eventBody: EventBody, token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.addEvent(token, eventBody)
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
    fun createPackage(token: String, packageBody: PackageBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.createPackage(token, packageBody)
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
    fun getEvents(token: String, vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getEvents(token, vendorId)
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
    fun getEventPackages(token: String, vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getPackagesVendor(token, vendorId)
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
    fun addNewEvent(token: String, eventBody: EventBodyRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.addNewEvent(token, eventBody)
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
    fun getAllEvents(token: String, vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getAllEvents(token, vendorId)
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

    fun getAllEventDates(token: String, vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.allEventDates(token, vendorId)
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
    fun getEventByDate(token: String, date: String, vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getEventByDate(token, date, vendorId)
            if (result is Result.Success) {
                _getEventByDate.value = result.value
            } else if (result is Result.Error) {
                _error.value = result.message
            }
            _isLoading.value = false
        }
    }

    fun getEventByCustomer(token: String, customerId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getEventByCustomer(token, customerId)
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
    fun addPayment(token: String,paymentBody: PaymentBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.addPayment(token, paymentBody)
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
    fun getAllCustomers(token: String,vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getAllCustomers(token,vendorId)
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
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }


    private val _paymentUrlResponse: MutableLiveData<ResponseBody> = MutableLiveData()
    val paymentUrlResponse: LiveData<ResponseBody> get() = _paymentUrlResponse


    fun updatePdfUrl(token: String,pDfBody: PdfBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.updatePdfUrl(token,pDfBody)
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

    fun getAllVendor(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getAllVendors(token)
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


    fun transferEvent(token: String,exposedBody: ExposedBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.transferEvent(token,exposedBody)
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

    fun eventExposedByMe(token: String,vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = false
            val result = repository.eventExposedByMe(token,vendorId)
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

    fun eventExposedToMe(token: String,vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = false
            val result = repository.eventExposedToMe(token,vendorId)
            if (result is Result.Success) {
                _eventExposedToMe.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }


    private val _getVendorsEvent: MutableLiveData<GetCustomerEventDataList> = MutableLiveData()
    val getVendorEvent: MutableLiveData<GetCustomerEventDataList> get() = _getVendorsEvent

    fun getEventByVendorId(token: String,vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = false
            val result = repository.getEventByVendorId(token,vendorId)
            if (result is Result.Success) {
                _getVendorsEvent.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

    private val _addVendorExpense: MutableLiveData<VendorExpenseResponse> = MutableLiveData()
    val addVendorExpense: MutableLiveData<VendorExpenseResponse> get() = _addVendorExpense

    fun addVendorExpense(token: String,vendorExpenseBody: VendorExpenseBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.addVendorExpense(token,vendorExpenseBody)
            if (result is Result.Success) {
                _addVendorExpense.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {

                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }


    private val _getVendorExpense: MutableLiveData<GetVendorExpenseResponse> = MutableLiveData()
    val getVendorExpense: MutableLiveData<GetVendorExpenseResponse> get() = _getVendorExpense

    fun getVendorExpenses(token: String,vendorId: String, type: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getVendorExpenses(token,vendorId, type)
            if (result is Result.Success) {
                _getVendorExpense.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
        }
    }


    private val _getReports: MutableLiveData<GetReportResponse> = MutableLiveData()
    val getReports: MutableLiveData<GetReportResponse> get() = _getReports


//    fun getReports(vendorId: String) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            val result = repository.getReports(vendorId)
//            if (result is Result.Success) {
//                _getReports.value = result.value
//                _isLoading.value = false
//            } else if (result is Result.Error) {
//                _error.value = result.message
//            }
//            _isLoading.value = false
//        }
//    }

    fun getReportsByDate(token:String,fromToDateBody: FromToDateBody, vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getReportsByDate(token,fromToDateBody, vendorId)
            if (result is Result.Success) {
                _getReports.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
            }
            _isLoading.value = false
        }

    }


    private val _addEmployee: MutableLiveData<AddEmployeeResponse> = MutableLiveData()
    val addEmployee: MutableLiveData<AddEmployeeResponse> get() = _addEmployee

    fun addEmployee(token: String,employeeBody: EmployeeBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.addEmployee(token,employeeBody)
            if (result is Result.Success) {
                _addEmployee.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }

    }

    private val _getEmployee: MutableLiveData<GetEmployeeResponse> = MutableLiveData()
    val getEmployee: MutableLiveData<GetEmployeeResponse> get() = _getEmployee
    fun getEmployee(token: String,vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getEmployee(token,vendorId)
            if (result is Result.Success) {
                _getEmployee.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

    private val _employeeExpenseResponse: MutableLiveData<EmployeeExpenseResponse> =
        MutableLiveData()
    val employeeExpenseResponse: MutableLiveData<EmployeeExpenseResponse> get() = _employeeExpenseResponse

    fun addEmployeeExpense(token: String,employeeExpenseBody: EmployeeExpenseBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.addEmployeeExpense(token,employeeExpenseBody)
            if (result is Result.Success) {
                _employeeExpenseResponse.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false

        }
    }

    private val _getEmployeeExpense: MutableLiveData<GetVendorExpenseResponse> = MutableLiveData()
    val getEmployeeExpense: MutableLiveData<GetVendorExpenseResponse> get() = _getEmployeeExpense

    fun getEmployeeExpenses(token: String,vendorId: String, type: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getEmployeeExpenses(token,vendorId, type)
            if (result is Result.Success) {
                _getEmployeeExpense.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
        }
    }


    private val _getImageResponse: MutableLiveData<ImageResponse> = MutableLiveData()
    val getImageResponse: MutableLiveData<ImageResponse> get() = _getImageResponse


    fun uploadImages(token: String,
        eventId: RequestBody, vendorId: RequestBody,
        images: ArrayList<MultipartBody.Part>
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.uploadImages(token,eventId, vendorId, images)
            if (result is Result.Success) {
                _getImageResponse.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }


    private val _getGallery: MutableLiveData<GalleryResponse> = MutableLiveData()
    val getGallery: LiveData<GalleryResponse> get() = _getGallery

    fun getGallery(token: String,vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getGallery(token,vendorId)
            if (result is Result.Success) {
                _getGallery.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

    private val _buyPackage: MutableLiveData<ResponseBody> = MutableLiveData()
    val buyPackage: LiveData<ResponseBody> get() = _buyPackage

    fun buyPackage(token: String,buyPackageBody: BuyPackageBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.buyPackage(token,buyPackageBody)
            if (result is Result.Success) {
                _buyPackage.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }


    private val _getMyPackage: MutableLiveData<MyPackageResponse> = MutableLiveData()
    val getMyPackage: LiveData<MyPackageResponse> get() = _getMyPackage

    fun getMyPackage(token: String,vendorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getMyPackage(token,vendorId)
            if (result is Result.Success) {
                _getMyPackage.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }


}