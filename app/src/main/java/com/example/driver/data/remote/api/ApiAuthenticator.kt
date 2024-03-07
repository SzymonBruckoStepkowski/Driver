package com.example.driver.data.remote.api

import com.example.driver.data.remote.DriverApi
import com.example.driver.domain.helper.AccountLogoutHelper
import com.example.driver.domain.provider.TokenProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ApiAuthenticator @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val accountHelper: AccountLogoutHelper,
    private val api: Provider<DriverApi>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val requestToken = response.request.header("Access-Token")

        val logoutCondition = true // Set to true temporarily
        if (requestToken.isNullOrEmpty() || logoutCondition) {
            // Called by 401 refresh token response
            CoroutineScope(context = Dispatchers.IO).launch {
                accountHelper.userLogout()
            }
            return null
        }

        val expireDate = tokenProvider.getAccessTokenCreateAndExpireDates().second

        return getRequestForRefreshedToken(expireDate, requestToken, response)
    }

    /*
     * @Synchronized method makes a queue for asynchronous operations
     * For many 401 responses, only one authenticate call should trigger refresh token
     * The rest waits for the response and don't meet the condition to trigger next refresh token
     */
    @Synchronized
    private fun getRequestForRefreshedToken(expireDate: String?, requestToken: String, response: Response): Request? {
        // For a changeable token value this condition may check if token value is different
        if (expireDate == tokenProvider.getAccessTokenCreateAndExpireDates().second
            && !tokenProvider.getToken().isNullOrEmpty()
            && requestToken.contains(tokenProvider.getToken()!!)
            && !tokenProvider.getRefreshToken().isNullOrEmpty()) {

            val tokenResponse = api.get().refreshToken(tokenProvider.getRefreshToken()!!)

            if (tokenResponse.isSuccessful) {
                val tokens = tokenResponse.body()
                if (tokens != null && tokens.accessToken.isNotEmpty()
                    && tokens.refreshToken.isNotEmpty()) {

                    tokenProvider.saveTokens(
                        tokens.accessToken,
                        tokens.refreshToken,
                        tokens.accessTokenExpiresOn,
                        tokens.refreshTokenExpiresOn
                    )

                    return response.request.newBuilder()
                        .header("Access-Token", tokens.accessToken)
                        .build()
                }
            }
            return null
        } else {
            return response.request.newBuilder()
                .header("Access-Token", tokenProvider.getToken().orEmpty())
                .build()
        }
    }
}