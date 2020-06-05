package com.mentarey.prototech.test.app.data.retrofit

import com.mentarey.prototech.test.app.data.retrofit.entity.ForexSignal
import com.mentarey.prototech.test.app.data.retrofit.entity.UserCredentials
import com.mentarey.prototech.test.app.ui.utils.NO_AUTH_HEADER
import retrofit2.http.*

interface PrototechApi {
    @POST("api/Authentication/RequestMoblieCabinetApiToken")
    @Headers(NO_AUTH_HEADER)
    suspend fun getUserToken(@Body userCredentials: UserCredentials): String

    @GET("clientmobile/GetAnalyticSignals/{login}")
    suspend fun getAnalyticSignals(
        @Path("login") login: String,
        @Query("pairs") pairsString: String,
        @Query("from") from: Long,
        @Query("to") to: Long,
        @Query("tradingsystem") tradingSystem: Int = 3
    ): List<ForexSignal>
}