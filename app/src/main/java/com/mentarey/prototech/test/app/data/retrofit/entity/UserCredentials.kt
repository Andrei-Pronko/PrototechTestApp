package com.mentarey.prototech.test.app.data.retrofit.entity

import com.google.gson.annotations.SerializedName

data class UserCredentials(
    @SerializedName("Login") val login: String,
    @SerializedName("Password") val password: String
)