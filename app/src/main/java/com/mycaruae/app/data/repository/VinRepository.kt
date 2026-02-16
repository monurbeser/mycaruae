package com.mycaruae.app.data.repository

import com.mycaruae.app.data.network.VinDecodedInfo
import com.mycaruae.app.data.network.VinDecoderApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VinRepository @Inject constructor(
    private val vinDecoderApi: VinDecoderApi,
) {
    suspend fun decodeVin(vin: String): Result<VinDecodedInfo> {
        return try {
            val response = vinDecoderApi.decodeVin(vin)
            val info = VinDecodedInfo.fromResults(response.results)
            if (info.make.isNotBlank()) {
                Result.success(info)
            } else {
                Result.failure(Exception("VIN not recognized. Please check and try again."))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network error. Please check your connection and try again."))
        }
    }

    fun isValidVin(vin: String): Boolean {
        return vin.length == 17 && vin.all { it.isLetterOrDigit() } && !vin.contains('I') && !vin.contains('O') && !vin.contains('Q')
    }
}