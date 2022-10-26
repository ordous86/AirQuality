package com.lucian.airquality

import com.google.gson.annotations.SerializedName
import com.lucian.airquality.WebService.WebDataStruct

/**
 * Repository of air quality.
 */
class AirRepository {
    // Define data structure for air quality.
    data class AirData(
        @SerializedName("county")
        val county: String,

        @SerializedName("pm2.5")
        val pm25: String,

        @SerializedName("siteid")
        val siteId: String,

        @SerializedName("sitename")
        val siteName: String,

        @SerializedName("status")
        val status: String
    )

    // Request online data.
    suspend fun requestOnlineData(): WebDataStruct =
        WebService.api.requestOnlineData()
}