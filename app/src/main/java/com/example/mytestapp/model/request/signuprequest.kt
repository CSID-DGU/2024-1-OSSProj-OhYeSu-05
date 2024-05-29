package com.example.mytestapp.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class signuprequest(
    @SerialName("ID")
    val ID: String, // VARCHAR in SQL
    @SerialName("Password")
    val Password: String,   // VARCHAR in SQL
    @SerialName("Name")
    val Name: String,   // VARCHAR in SQL
    @SerialName("Gender")
    val Gender: String,     // VARCHAR in SQL
    @SerialName("StudentID")
    val StudentID: String   // VARCHAR in SQL
)