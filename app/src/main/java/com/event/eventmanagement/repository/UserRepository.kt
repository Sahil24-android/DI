package com.event.eventmanagement.repository

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
import com.event.eventmanagement.views.fragment.datasource.PackageBody
import com.event.eventmanagement.views.fragment.datasource.PackageResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody

class UserRepository() {

    private val apiInterface = RetrofitClient.network
    private val apiInterfaceLocation = RetrofitClient.locationNetwork
    suspend fun getServices(): Result<ServicesData> {
        return try {
            val response = apiInterface.getServices()
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
            val response = apiInterface.getServices(serviceId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getLocationData(pinCode: String): Result<ArrayList<PinCodeData>> {
        return try {
            val response = apiInterfaceLocation.getDataFromPinCode(pinCode)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun registerUser(
        data: RegistrationDataSend,
        multipartFormData: MultipartBody.Part
    ): Result<RegisterUserResponse> {
        return try {
            val companyName = RequestBody.create("text/plain".toMediaTypeOrNull(), data.companyName)
            val ownerName = RequestBody.create("text/plain".toMediaTypeOrNull(), data.ownerName)
            val city = RequestBody.create("text/plain".toMediaTypeOrNull(), data.city)
            val state = RequestBody.create("text/plain".toMediaTypeOrNull(), data.state)
            val country = RequestBody.create("text/plain".toMediaTypeOrNull(), data.country)
            val pinCode = RequestBody.create("text/plain".toMediaTypeOrNull(), data.pinCodeData)
            val address = RequestBody.create("text/plain".toMediaTypeOrNull(), data.address)
            val mobileNumber =
                RequestBody.create("text/plain".toMediaTypeOrNull(), data.mobileNumber)
            val aleternateNumber =
                RequestBody.create("text/plain".toMediaTypeOrNull(), data.alternativeMobileNumber)
            val serviceId = RequestBody.create("text/plain".toMediaTypeOrNull(), data.serviceId)
            val servicePackageId =
                RequestBody.create("text/plain".toMediaTypeOrNull(), data.servicePackageId)
            val password = RequestBody.create("text/plain".toMediaTypeOrNull(), data.password)
            val locationId = RequestBody.create("text/plain".toMediaTypeOrNull(), "0")
            val isActive = RequestBody.create("text/plain".toMediaTypeOrNull(), "0")

            val response = apiInterface.registerUser(
                multipartFormData,
                serviceId,
                servicePackageId,
                companyName,
                ownerName,
                mobileNumber,
                aleternateNumber,
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

    private fun createPartFromString(descriptionString: String): RequestBody {
        return RequestBody.create(
            okhttp3.MultipartBody.FORM, descriptionString
        )
    }


    suspend fun addEvent(eventRequest: EventBody): Result<EventResponse> {
        return try {
            val response = apiInterface.createEvent(eventRequest)
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
            val response = apiInterface.createPackage(packageBody)
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
            val response = apiInterface.getEvents(vendorId)
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
            val response = apiInterface.getEventPackage(vendorId)
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
            val response = apiInterface.addNewEvent(eventBodyRequest)
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
            val response = apiInterface.getAllCustomerEvents(vendorId)
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
            val response = apiInterface.getEventDateForCalendar(vendorId)
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
            val response = apiInterface.getEventByDate(date, vendorId)
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
            val response = apiInterface.getEventByCustomer(customerId)
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
            val response = apiInterface.addPayment(paymentBody)
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
            val response = apiInterface.getAllCustomer()
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
            val response = apiInterface.login(loginBody)
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
            val response = apiInterface.updatePdfUrl(pdfBody)
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
            val response = apiInterface.getAllVendors()
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
            val response = apiInterface.transferEvent(exposedBody)
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
            val response = apiInterface.eventExposedByMe(vendorId)
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
            val response = apiInterface.eventExposedToMe(vendorId)
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
            val response = apiInterface.getEventByVendorId(vendorId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun addVendorExpense(expenseBody: VendorExpenseBody):Result<VendorExpenseResponse>{
        return try {
            val response = apiInterface.addVendorExpense(expenseBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }

        }catch (e:Exception){
            Result.Error("Exception occurred: ${e.message}")
        }
    }

}