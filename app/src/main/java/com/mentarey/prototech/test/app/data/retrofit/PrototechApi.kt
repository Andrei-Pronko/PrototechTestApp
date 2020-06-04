package com.mentarey.prototech.test.app.data.retrofit

import com.mentarey.prototech.test.app.data.retrofit.entity.ForexSignal
import com.mentarey.prototech.test.app.data.retrofit.entity.UserCredentials
import retrofit2.http.*

interface PrototechApi {
    @POST("api/Authentication/RequestMoblieCabinetApiToken")
    suspend fun getUserToken(@Body userCredentials: UserCredentials): String

    @GET("clientmobile/GetAnalyticSignals/{login}")
    suspend fun getAnalyticSignals(
        @Header("passkey") token: String,
        @Path("login") login: String,
        @Query("pairs") pairsString: String,
        @Query("from") from: Long = 1479860023,
        @Query("to") to: Long = 1480066860,
        @Query("tradingsystem") tradingSystem: Int = 3
    ): List<ForexSignal>
}