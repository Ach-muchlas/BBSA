package com.am.bbsa.ui.auth

import androidx.lifecycle.ViewModel
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
        repository.register(name, NIK, gender, address, phoneNumber, gender)


    fun saveCredentialUser(token: LoginResult) = userPref.saveCredentialUser(token)
    fun isLogin() = userPref.isUserLogin()
    fun getCredentialUser(): LoginResult? = userPref.getCredentialUser()
    fun clearCredentialUser() = userPref.clearCredentialUser()

    fun setSessionTimeout() = userPref.setSessionTimeOut()
    fun isSessionExpired(): Boolean = userPref.isSessionExpired()

    fun isAdminUser(): Boolean = userPref.isAdminUser()
}
