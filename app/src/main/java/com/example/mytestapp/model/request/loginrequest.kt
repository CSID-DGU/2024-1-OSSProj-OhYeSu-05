package com.example.mytestapp.model.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class loginrequest(
    @SerialName("UserID")
    val UserID: String, // VARCHAR in SQL
    @SerialName("Password")
    val Password: String    // VARCHAR in SQL
)