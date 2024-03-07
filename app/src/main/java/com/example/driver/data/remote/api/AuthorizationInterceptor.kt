package com.example.driver.data.remote.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import com.example.driver.domain.provider.TokenProvider

class AuthorizationInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val builder = if (!tokenProvider.getToken().isNullOrEmpty()
            && !request.url.toString().contains("token") //have to exclude refreshToken request
        ) {
            request.newBuilder()
                .addHeader("Authorization", tokenProvider.getToken()!!)
        } else {
            request.newBuilder()
        }
            .addHeader("X-Frame-Options", "DENY")
            .addHeader("X-XSS-Protection", "1")
            .addHeader("X-Content-Type-Options", "nosniff")
            .method(request.method, request.body)

        request = builder.build()
        return chain.proceed(request)
    }
}