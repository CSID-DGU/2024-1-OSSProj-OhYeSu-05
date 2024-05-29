package com.example.mytestapp.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BlockData(
    @SerialName("BlockID")
    val BlockID: String,
    @SerialName("UserID")
    val UserID: String,
    @SerialName("BlockerID")
    val BlockerID: String,
    @SerialName("BlockedID")
    val BlockedID: String,
    @SerialName("Timestamp")
    val timestamp: String = System.currentTimeMillis().toString()  // 기본값으로 현재 시간을 사용
)
