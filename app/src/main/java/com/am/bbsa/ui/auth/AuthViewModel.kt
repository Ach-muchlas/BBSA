package com.am.bbsa.ui.auth

import androidx.lifecycle.ViewModel
import com.am.bbsa.data.response.DataItemRegister
import com.am.bbsa.data.response.LoginResult
import com.am.bbsa.service.source.Repository
import com.am.bbsa.service.source.UserPreferences

class AuthViewModel(private val repository: Repository) : ViewModel() {
    private val userPref = UserPreferences.getInstance()

    fun login(phoneNumber: String, password: String) = repository.login(phoneNumber, password)

    fun register(
        name: String,
        NIK: String,
        gender: String,
        address: String,
        phoneNumber: String,
        password: String
    ) =
        repository.register(name, NIK, gender, address, phoneNumber, password)

    fun saveCredentialUser(token: LoginResult) = userPref.saveCredentialUser(token)
    fun getCredentialUser(): LoginResult? = userPref.getCredentialUser()
    fun saveCredentialRegister(data: DataItemRegister) = userPref.saveCredentialRegis(data)
    fun getCredentialRegister(): DataItemRegister = userPref.getCredentialRegis()
    fun isLogin() = userPref.isUserLogin()
    fun clearCredentialUser() = userPref.clearCredentialUser()
    fun setSessionTimeout() = userPref.setSessionTimeOut()
    fun isSessionExpired(): Boolean = userPref.isSessionExpired()
    fun isAdminUser(): Boolean = userPref.isAdminUser()
    fun verificationOtpRegister(userId: Int, numberOtp: String) =
        repository.verificationOtpRegister(userId, numberOtp)

    fun verificationOtpForgotPassword(userId: Int, numberOtp: String) =
        repository.verificationOtpForgotPassword(userId, numberOtp)

    fun resendingOtpRegister(phoneNumber: String) = repository.resendingOtpRegister(phoneNumber)
    fun resendingOtpForgotPassword(phoneNumber: String) =
        repository.resendingOtpForgotPassword(phoneNumber)

    fun forgotPassword(phoneNumber: String) = repository.forgotPassword(phoneNumber)
    fun resetPassword(userId: Int, password: String) = repository.resetPassword(userId, password)

    fun saveOtpStatus(isOtpPending: Boolean) = userPref.saveOtpStatus(isOtpPending)

    fun isOtpPending(): Boolean = userPref.isOtpPending()


}
