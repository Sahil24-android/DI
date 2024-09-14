package com.event.eventmanagement.apis

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitClient {

    const val BASE_URL = "http://backend.eventsgo.in/api/"

//    http://13.201.188.94:7000/api/service

    private const val LOCATION_URL = "https://api.postalpincode.in/"
    private const val BASE_URL2 = "https://api.phonepe.com/"
//    private const val BASE_URL= "https://api-preprod.phonepe.com/apis/pg-sandbox"
    private var gson = GsonBuilder().setLenient().create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    private val retrofitLocation: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(LOCATION_URL)
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getPG_SDKInterface(): ApiServices {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices::class.java)
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }

            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    val network: ApiServices by lazy {
        retrofit.create(ApiServices::class.java)
    }

    val locationNetwork: ApiServices by lazy {
        retrofitLocation.create(ApiServices::class.java)
    }

}