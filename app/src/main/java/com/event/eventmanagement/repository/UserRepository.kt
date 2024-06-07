package com.event.eventmanagement.repository

import com.event.eventmanagement.apis.ApiServices
import com.event.eventmanagement.apis.Result
import com.event.eventmanagement.apis.RetrofitClient
import com.event.eventmanagement.views.activity.addeventPayments.PaymentBody
import com.event.eventmanagement.views.activity.addeventPayments.PaymentResponse
import com.event.eventmanagement.views.activity.createCustomerEvent.EventBodyRequest
import com.event.eventmanagement.views.activity.createCustomerEvent.NewEventResponse
import com.event.eventmanagement.views.activity.customerEventList.data.GetCustomerEventDataList
import com.event.eventmanagement.views.activity.exposed.data.ExposedBody
import com.event.eventmanagement.views.activity.exposed.data.ExposedResponse
import com.event.eventmanagement.views.activity.exposed.data.VendorListResponse
import com.event.eventmanagement.views.activity.invoice.data.PdfBody
import com.event.eventmanagement.views.activity.report.data.FromToDateBody
import com.event.eventmanagement.views.activity.vendorExpense.data.VendorExpenseBody
import com.event.eventmanagement.views.activity.vendorExpense.data.VendorExpenseResponse
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
import com.event.eventmanagement.views.fragment.datasource.GetReportResponse
import com.event.eventmanagement.views.fragment.datasource.GetVendorExpenseResponse
import com.event.eventmanagement.views.fragment.datasource.PackageBody
import com.event.eventmanagement.views.fragment.datasource.PackageResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiServices: ApiServices ) {

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

    suspend fun getServicesVendor(serviceId: String): Result<PackageData> {
        return try {
            val response = apiServices.getServices(serviceId)
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
            val servicePackageId =
                data.servicePackageId.toRequestBody("text/plain".toMediaTypeOrNull())
            val password = data.password.toRequestBody("text/plain".toMediaTypeOrNull())
            val locationId = "0".toRequestBody("text/plain".toMediaTypeOrNull())
            val isActive = "0".toRequestBody("text/plain".toMediaTypeOrNull())

            val response = apiServices.registerUser(
                multipartFormData,
                serviceId,
                servicePackageId,
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




    suspend fun addEvent(eventRequest: EventBody): Result<EventResponse> {
        return try {
            val response = apiServices.createEvent(eventRequest)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }


    suspend fun createPackage(packageBody: PackageBody): Result<PackageResponse> {
        return try {
            val response = apiServices.createPackage(packageBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getEvents(vendorId: String): Result<AllEventsResponse> {
        return try {
            val response = apiServices.getEvents(vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getPackagesVendor(vendorId: String): Result<AllPackageResponse> {
        return try {
            val response = apiServices.getEventPackage(vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun addNewEvent(eventBodyRequest: EventBodyRequest): Result<NewEventResponse> {
        return try {
            val response = apiServices.addNewEvent(eventBodyRequest)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getAllEvents(vendorId: String): Result<GetCustomerEventDataList> {
        return try {
            val response = apiServices.getAllCustomerEvents(vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun allEventDates(vendorId: String): Result<AllEventDates> {
        return try {
            val response = apiServices.getEventDateForCalendar(vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getEventByDate(date: String, vendorId: String): Result<GetCustomerEventDataList> {
        return try {
            val response = apiServices.getEventByDate(date, vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getEventByCustomer(customerId: String): Result<GetCustomerEventDataList> {
        return try {
            val response = apiServices.getEventByCustomer(customerId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun addPayment(paymentBody: PaymentBody): Result<PaymentResponse> {
        return try {
            val response = apiServices.addPayment(paymentBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }


    suspend fun getAllCustomers(): Result<CustomerResponse> {
        return try {
            val response = apiServices.getAllCustomer()
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


    suspend fun updatePdfUrl(pdfBody: PdfBody): Result<ResponseBody> {
        return try {
            val response = apiServices.updatePdfUrl(pdfBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getAllVendors(): Result<VendorListResponse> {
        return try {
            val response = apiServices.getAllVendors()
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }


    suspend fun transferEvent(exposedBody: ExposedBody): Result<ExposedResponse> {
        return try {
            val response = apiServices.transferEvent(exposedBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }


    suspend fun eventExposedByMe(vendorId: String): Result<GetCustomerEventDataList> {
        return try {
            val response = apiServices.eventExposedByMe(vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Failed to get services: ${e.message}")
        }
    }

    suspend fun eventExposedToMe(vendorId: String): Result<GetCustomerEventDataList> {
        return try {
            val response = apiServices.eventExposedToMe(vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }

        } catch (e: Exception) {
            Result.Error("Failed to get services: ${e.message}")
        }

    }


    suspend fun getEventByVendorId(vendorId: String): Result<GetCustomerEventDataList> {
        return try {
            val response = apiServices.getEventByVendorId(vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun addVendorExpense(expenseBody: VendorExpenseBody): Result<VendorExpenseResponse> {
        return try {
            val response = apiServices.addVendorExpense(expenseBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }

        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getVendorExpenses(vendorId: String, type: String): Result<GetVendorExpenseResponse> {
        return try {
            val response = apiServices.getVendorExpenses(vendorId, type)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }




    suspend fun getReportsByDate(
        fromToDateBody: FromToDateBody,
        vendorId: String
    ): Result<GetReportResponse> {
        return try {
            val response = apiServices.getReportsByDate(fromToDateBody, vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            }else{
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")

            }
    }
}