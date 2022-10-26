package com.lucian.airquality

import com.google.gson.annotations.SerializedName
import com.lucian.airquality.AirRepository.AirData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Service for web access.
 */
object WebService {
    // Fields.
    val api: WebApi by lazy {
        Retrofit.Builder()
            .baseUrl(SOURCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebApi::class.java)
    }

    // Define interface for web APIs.
    interface WebApi {
        @GET("aqx_p_432?limit=$DATA_COUNT&api_key=$KEY&sort=ImportDate%20desc&format=json")
        suspend fun requestOnlineData(): WebDataStruct
    }

    // Define structure for web data.
    data class WebDataStruct (
        @SerializedName("records")
        val records: List<AirData>
    )
}