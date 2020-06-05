package com.mentarey.prototech.test.app.data.retrofit

import com.mentarey.prototech.test.app.ui.utils.AUTHORIZATION_HEADER
import com.mentarey.prototech.test.app.ui.utils.EMPTY_LINE
import com.mentarey.prototech.test.app.ui.utils.NO_AUTH_HEADER
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {

    var authToken: String = EMPTY_LINE

    override fun intercept(chain: Interceptor.Chain): Response {
        var originalRequest = chain.request()
        val noAuthHeader = originalRequest.header(NO_AUTH_HEADER)
        if (noAuthHeader.isNullOrEmpty())
            originalRequest = originalRequest.newBuilder().apply {
                if (authToken.isNotEmpty())
                    header(AUTHORIZATION_HEADER, authToken)
            }.build()
        return chain.proceed(originalRequest)
    }
}