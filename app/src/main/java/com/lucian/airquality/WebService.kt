package com.lucian.airquality

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        // TODO: Define web APIs.
    }
}