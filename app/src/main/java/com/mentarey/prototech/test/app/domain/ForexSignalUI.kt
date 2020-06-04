package com.mentarey.prototech.test.app.domain

data class ForexSignalUI(
    val id: Long,
    val actualTime: String,
    val pair: String,
    val period: String,
    val price: String,
    val comment: String
)