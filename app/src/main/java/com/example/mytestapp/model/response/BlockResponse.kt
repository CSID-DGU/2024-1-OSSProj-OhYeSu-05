package com.example.mytestapp.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("error") val error: String? = null
)
