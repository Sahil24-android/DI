package com.event.eventmanagement.apis

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
import com.event.eventmanagement.views.auth.datasource.ServicesData
import com.event.eventmanagement.views.fragment.datasource.AllEventDates
import com.event.eventmanagement.views.fragment.datasource.AllEventsResponse
import com.event.eventmanagement.views.fragment.datasource.AllPackageResponse
import com.event.eventmanagement.views.fragment.datasource.CustomerResponse
import com.event.eventmanagement.views.fragment.datasource.EventBody
import com.event.eventmanagement.views.fragment.datasource.EventResponse
import com.event.eventmanagement.views.fragment.datasource.GetReportResponse
import com.event.eventmanagement.views.fragment.datasource.GetVendorExpenseReponse
import com.event.eventmanagement.views.fragment.datasource.PackageBody
import com.event.eventmanagement.views.fragment.datasource.PackageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiServices {


    @GET("service")
    suspend fun getServices(): Response<ServicesData>


    @GET("service/{serviceId}")
    suspend fun getServices(
        @Path("serviceId") serviceId: String
    ): Response<PackageData>


    @GET("pincode/{pincode}")
    suspend fun getDataFromPinCode(
        @Path("pincode") pincode: String
    ): Response<ArrayList<PinCodeData>>


    //    service_id:1
//    service_pkg_id:1
//    company_name:shasha
//    owner_name:sns
//    mob_no:1234567890
//    alt_mob_no:1234567890
//    address:accddv fvxv
//    pincode:440034
//    location_id:1
//    country_id:1
//    state_id:1
//    is_active:0
//    city_id:1
//    logo_image:
    @Multipart
    @POST("vendor")
    suspend fun registerUser(
        @Part logo_image: MultipartBody.Part,
        @Part("service_id") service_id: RequestBody,
        @Part("service_pkg_id") service_pkg_id: RequestBody,
        @Part("company_name") company_name: RequestBody,
        @Part("owner_name") owner_name: RequestBody,
        @Part("mob_no") mob_no: RequestBody,
        @Part("alt_mob_no") alt_mob_no: RequestBody,
        @Part("address") address: RequestBody,
        @Part("pincode") pincode: RequestBody,
        @Part("location_id") location_id: RequestBody,
        @Part("country_id") country_id: RequestBody,
        @Part("state_id") state_id: RequestBody,
        @Part("is_active") is_active: RequestBody,
        @Part("city_id") city_id: RequestBody,
        @Part("password") password: RequestBody
    ): Response<RegisterUserResponse>


    @POST("event/addEvent")
    suspend fun createEvent(@Body eventRequest: EventBody): Response<EventResponse>

    @GET("event/{vendorId}")
    suspend fun getEvents(
        @Path("vendorId") vendor_id: String
    ): Response<AllEventsResponse>

    @POST("event/eventPackage")
    suspend fun createPackage(@Body packageBody: PackageBody): Response<PackageResponse>


    @GET("event/eventPackage/{vendorId}")
    suspend fun getEventPackage(
        @Path("vendorId") vendor_id: String
    ): Response<AllPackageResponse>


    @POST("event/eventadd")
    suspend fun addNewEvent(@Body eventBodyRequest: EventBodyRequest): Response<NewEventResponse>


    @GET("event/geteventofCust/{vendorId}")
    suspend fun getAllCustomerEvents(
        @Path("vendorId") vendor_id: String
    ): Response<GetCustomerEventDataList>

    @GET("event/geteventDates/{vendorId}")
    suspend fun getEventDateForCalendar(
        @Path("vendorId") vendor_id: String
    ): Response<AllEventDates>

    @GET("event/geteventbydate/{eventDate}/{vendorId}")
    suspend fun getEventByDate(
        @Path("eventDate") eventDate: String,
        @Path("vendorId") vendor_id: String
    ): Response<GetCustomerEventDataList>

    @GET("event/getCustomerEvents/{customerId}")
    suspend fun getEventByCustomer(
        @Path("customerId") customerId: String
    ): Response<GetCustomerEventDataList>

    @POST("event/Makepayment")
    suspend fun addPayment(@Body paymentBody: PaymentBody): Response<PaymentResponse>

    @GET("customer")
    suspend fun getAllCustomer(): Response<CustomerResponse>


    @POST("vendor/login")
    suspend fun login(@Body loginBody: LoginBody): Response<LoginResponse>


    @POST("event/updatePaymentUrl")
    suspend fun updatePdfUrl(@Body pdfBody: PdfBody): Response<ResponseBody>


    @GET("vendor")
    suspend fun getAllVendors(): Response<VendorListResponse>

    @POST("event/TransferEvent")
    suspend fun transferEvent(@Body exposedBody: ExposedBody): Response<ExposedResponse>

    @GET("event/Exposing/{vendorId}")
    suspend fun eventExposedByMe(
        @Path("vendorId") vendor_id: String
    ): Response<GetCustomerEventDataList>

    @GET("event/ExposedTo/{vendorId}")
    suspend fun eventExposedToMe(
        @Path("vendorId") vendor_id: String
    ): Response<GetCustomerEventDataList>

    @GET("event/getVendorList/{vendorId}")
    suspend fun getEventByVendorId(
        @Path("vendorId") vendor_id: String
    ): Response<GetCustomerEventDataList>


    @POST("expense/addExpense")
    suspend fun addVendorExpense(
        @Body vendorExpenseBody: VendorExpenseBody
    ): Response<VendorExpenseResponse>


    @GET("event/Getexpense/{vendorId}/{type}")
    suspend fun getVendorExpenses(
        @Path("vendorId") vendor_id: String,
        @Path("type") type: String
    ): Response<GetVendorExpenseReponse>

    @GET("event/getAllTotal/{vendorId}")
    suspend fun getReports(
        @Path("vendorId") vendor_id: String
    ):Response<GetReportResponse>
}