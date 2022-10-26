package com.lucian.airquality

/**
 * Define constants for global access.
 */
interface Constants

const val DATA_COUNT = 1000
const val KEY = "cebebe84-e17d-4022-a28f-81097fda5896"
const val PM_25_THRESHOLD = 10
const val SOURCE_URL = "https://data.epa.gov.tw/api/v2/"
const val TAG = "AirQualityApp"

enum class QueryState {
    ERROR,
    IDLE,
    RUNNING,
    SUCCESS
}