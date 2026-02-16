package com.mycaruae.app.data.network

import com.google.gson.annotations.SerializedName

data class VinDecoderResponse(
    @SerializedName("Results")
    val results: List<VinResult>,
)

data class VinResult(
    @SerializedName("Variable")
    val variable: String,
    @SerializedName("Value")
    val value: String?,
)

data class VinDecodedInfo(
    val make: String,
    val model: String,
    val year: String,
    val bodyClass: String,
    val engineSize: String,
    val fuelType: String,
    val driveType: String,
) {
    companion object {
        fun fromResults(results: List<VinResult>): VinDecodedInfo {
            fun find(varName: String): String {
                return results
                    .firstOrNull { it.variable.equals(varName, ignoreCase = true) }
                    ?.value
                    ?.takeIf { it.isNotBlank() }
                    ?: ""
            }

            return VinDecodedInfo(
                make = find("Make"),
                model = find("Model"),
                year = find("Model Year"),
                bodyClass = find("Body Class"),
                engineSize = find("Displacement (L)"),
                fuelType = find("Fuel Type - Primary"),
                driveType = find("Drive Type"),
            )
        }
    }
}