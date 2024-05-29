package com.example.mytestapp.model.response

import com.example.mytestapp.model.request.ChatMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDataResponse(
    @SerialName("messages") val messages: List<ChatMessage>,
    @SerialName("success") val success: Boolean,
    @SerialName("error") val error: String? = null
)

