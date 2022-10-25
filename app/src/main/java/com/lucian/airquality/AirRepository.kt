package com.lucian.airquality

/**
 * Repository of air quality.
 */
class AirRepository {
    // Define data structure for air quality.
    data class AirData(
        // TODO: Replace mockup data.
        val county: String = "Taipei",  // mockup
        val pm25: String = "10",        // mockup
        val siteId: String = "0",       // mockup
        val siteName: String = "XinYi", // mockup
        val status: String = "Good"     // mockup
    )

    // Request online data.
    fun requestOnlineData() = mutableListOf<AirData>().apply {
        // TODO: Replace mockup data
        for (i in 0 until 20) {
            add(AirData())
        }
    }
}