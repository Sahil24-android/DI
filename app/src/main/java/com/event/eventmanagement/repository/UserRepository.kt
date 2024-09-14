package com.event.eventmanagement.repository

import com.event.eventmanagement.apis.ApiServices
import com.event.eventmanagement.apis.Result
import com.event.eventmanagement.usersession.PreferenceManager
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
import com.google.android.gms.common.api.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiServices: ApiServices) {

    // private val apiInterfaceLocation = RetrofitClient.locationNetwork
    suspend fun getServices(): Result<ServicesData> {
        return try {
            val response = apiServices.getServices()
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getServicesVendor(token: String,serviceId: String): Result<PackageData> {
        return try {
            val response = apiServices.getServices(token,serviceId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

//    suspend fun getLocationData(pinCode: String): Result<ArrayList<PinCodeData>> {
//        return try {
//            val response = apiInterfaceLocation.getDataFromPinCode(pinCode)
//            if (response.isSuccessful) {
//                Result.Success(response.body()!!)
//            } else {
//                Result.Error("Failed to get services: ${response.message()}")
//            }
//        } catch (e: Exception) {
//            Result.Error("Exception occurred: ${e.message}")
//        }
//    }

    suspend fun registerUser(
        data: RegistrationDataSend,
        multipartFormData: MultipartBody.Part
    ): Result<RegisterUserResponse> {
        return try {
            val companyName = data.companyName.toRequestBody("text/plain".toMediaTypeOrNull())
            val ownerName = data.ownerName.toRequestBody("text/plain".toMediaTypeOrNull())
            val city = data.city.toRequestBody("text/plain".toMediaTypeOrNull())
            val state = data.state.toRequestBody("text/plain".toMediaTypeOrNull())
            val country = data.country.toRequestBody("text/plain".toMediaTypeOrNull())
            val pinCode = data.pinCodeData.toRequestBody("text/plain".toMediaTypeOrNull())
            val address = data.address.toRequestBody("text/plain".toMediaTypeOrNull())
            val mobileNumber =
                data.mobileNumber.toRequestBody("text/plain".toMediaTypeOrNull())
            val alternateNumber =
                data.alternativeMobileNumber.toRequestBody("text/plain".toMediaTypeOrNull())
            val serviceId = data.serviceId.toRequestBody("text/plain".toMediaTypeOrNull())
            val regDate =
                data.regDate.toRequestBody("text/plain".toMediaTypeOrNull())
            val password = data.password.toRequestBody("text/plain".toMediaTypeOrNull())
            val locationId = "0".toRequestBody("text/plain".toMediaTypeOrNull())
            val isActive = "0".toRequestBody("text/plain".toMediaTypeOrNull())

            val response = apiServices.registerUser(
                multipartFormData,
                serviceId,
                regDate,
                companyName,
                ownerName,
                mobileNumber,
                alternateNumber,
                address,
                pinCode,
                locationId,
                country,
                state,
                isActive,
                city, password
            )
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }


    suspend fun addEvent(token:String,eventRequest: EventBody): Result<EventResponse> {
        return try {
            val response = apiServices.createEvent(token,eventRequest)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }


    suspend fun createPackage(token:String,packageBody: PackageBody): Result<PackageResponse> {
        return try {
            val response = apiServices.createPackage(token,packageBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getEvents(token: String,vendorId: String): Result<AllEventsResponse> {
        return try {
            val response = apiServices.getEvents(token,vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getPackagesVendor(token: String,vendorId: String): Result<AllPackageResponse> {
        return try {
            val response = apiServices.getEventPackage(token,vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun addNewEvent(token: String,eventBodyRequest: EventBodyRequest): Result<NewEventResponse> {
        return try {
            val response = apiServices.addNewEvent(token,eventBodyRequest)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getAllEvents(token: String,vendorId: String): Result<GetCustomerEventDataList> {
        return try {
            val response = apiServices.getAllCustomerEvents(token,vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun allEventDates(token: String,vendorId: String): Result<AllEventDates> {
        return try {
            val response = apiServices.getEventDateForCalendar(token,vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getEventByDate(token: String,date: String, vendorId: String): Result<GetCustomerEventDataList> {
        return try {
            val response = apiServices.getEventByDate(token,date, vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getEventByCustomer(token: String,customerId: String): Result<GetCustomerEventDataList> {
        return try {
            val response = apiServices.getEventByCustomer(token,customerId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun addPayment(token: String,paymentBody: PaymentBody): Result<PaymentResponse> {
        return try {
            val response = apiServices.addPayment(token,paymentBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }


    suspend fun getAllCustomers(token: String,vendorId: String): Result<CustomerResponse> {
        return try {
            val response = apiServices.getAllCustomer(token,vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")

        }
    }

    suspend fun login(loginBody: LoginBody): Result<LoginResponse> {
        return try {
            val response = apiServices.login(loginBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }


    suspend fun updatePdfUrl(token: String,pdfBody: PdfBody): Result<ResponseBody> {
        return try {
            val response = apiServices.updatePdfUrl(token,pdfBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getAllVendors(token: String): Result<VendorListResponse> {
        return try {
            val response = apiServices.getAllVendors(token)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }


    suspend fun transferEvent(token: String,exposedBody: ExposedBody): Result<ExposedResponse> {
        return try {
            val response = apiServices.transferEvent(token,exposedBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }


    suspend fun eventExposedByMe(token: String,vendorId: String): Result<GetCustomerEventDataList> {
        return try {
            val response = apiServices.eventExposedByMe(token,vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Failed to get services: ${e.message}")
        }
    }

    suspend fun eventExposedToMe(token: String,vendorId: String): Result<GetCustomerEventDataList> {
        return try {
            val response = apiServices.eventExposedToMe(token,vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }

        } catch (e: Exception) {
            Result.Error("Failed to get services: ${e.message}")
        }

    }


    suspend fun getEventByVendorId(token: String,vendorId: String): Result<GetCustomerEventDataList> {
        return try {
            val response = apiServices.getEventByVendorId(token,vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun addVendorExpense(token:String,expenseBody: VendorExpenseBody): Result<VendorExpenseResponse> {
        return try {
            val response = apiServices.addVendorExpense(token,expenseBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }

        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getVendorExpenses(token: String,
        vendorId: String,
        type: String
    ): Result<GetVendorExpenseResponse> {
        return try {
            val response = apiServices.getVendorExpenses(token,vendorId, type)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }


    suspend fun getReportsByDate(token: String,
        fromToDateBody: FromToDateBody,
        vendorId: String
    ): Result<GetReportResponse> {
        return try {
            val response = apiServices.getReportsByDate(token,fromToDateBody, vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")

        }
    }

    suspend fun addEmployee(token: String,employeeBody: EmployeeBody): Result<AddEmployeeResponse> {
        return try {
            val response = apiServices.addEmployee(token,employeeBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getEmployee(token: String,vendorId: String): Result<GetEmployeeResponse> {
        return try {
            val response = apiServices.getEmployee(token,vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun addEmployeeExpense(token: String,employeeExpenseBody: EmployeeExpenseBody): Result<EmployeeExpenseResponse> {
        return try {
            val response = apiServices.addEmployeeExpense(token,employeeExpenseBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }

    }

    suspend fun getEmployeeExpenses(token: String,
        vendorId: String,
        type: String
    ): Result<GetVendorExpenseResponse> {
        return try {
            val response = apiServices.getVendorExpenses(token,vendorId, type)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun uploadImages(token: String,
        eventId: RequestBody, vendorId: RequestBody,
        images: ArrayList<MultipartBody.Part>
    ): Result<ImageResponse> {
        return try {
            val response = apiServices.uploadImages(token,eventId, vendorId, images)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }


    suspend fun getGallery(token:String,vendorId: String): Result<GalleryResponse> {
        return try {
            val response = apiServices.getGallery(token,vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun buyPackage(token:String,buyPackageBody: BuyPackageBody): Result<ResponseBody> {
        return try {
            val response = apiServices.buyPackage(token,buyPackageBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getMyPackage(token: String,vendorId: String): Result<MyPackageResponse> {
        return try {
            val response = apiServices.getPackageBuy(token,vendorId)
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