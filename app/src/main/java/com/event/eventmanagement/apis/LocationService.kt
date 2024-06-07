package com.event.eventmanagement.apis

import com.event.eventmanagement.views.auth.datasource.PinCodeData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationService {
    @GET("pincode/{pincode}")
    suspend fun getDataFromPinCode(
        @Path("pincode") pincode: String
    ): Response<ArrayList<PinCodeData>>

}