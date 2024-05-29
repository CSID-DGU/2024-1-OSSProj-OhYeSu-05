package com.example.mytestapp.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    @SerialName("messageId")
    val messageId: String,
    @SerialName("receiverId")
    val receiverId: String,
    @SerialName("senderId")
    val senderId: String,
    @SerialName("content")
    val content: String,
    @SerialName("timestamp")
    val timestamp: String,
    @SerialName("formattedTimestamp")
    val formattedTimestamp: String, // 백엔드에서 포맷된 타임스탬프
    @SerialName("senderName")
    val senderName: String // 백엔드에서 처리된 송신자 이름
)
