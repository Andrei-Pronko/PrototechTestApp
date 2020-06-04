package com.mentarey.prototech.test.app.data.retrofit.entity

import com.google.gson.annotations.SerializedName

data class UserToken(
    @SerializedName("passkey") val passkey: String
)