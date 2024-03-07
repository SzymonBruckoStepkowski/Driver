package com.example.driver.domain.use_case.logout

import com.example.driver.domain.helper.AccountLogoutHelper
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val accountLogoutHelper: AccountLogoutHelper
) {
    suspend operator fun invoke() {
        accountLogoutHelper.userLogout()
    }
}