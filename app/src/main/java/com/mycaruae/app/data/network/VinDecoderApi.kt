package com.mycaruae.app.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface VinDecoderApi {

    @GET("vehicles/decodevin/{vin}?format=json")
    suspend fun decodeVin(@Path("vin") vin: String): VinDecoderResponse
}