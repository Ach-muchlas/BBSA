package com.am.bbsa.service.source

import android.content.Context
import android.content.SharedPreferences
import com.am.bbsa.data.response.LoginResult

class UserPreferences private constructor() {
    private lateinit var sharePref: SharedPreferences

    fun init(context: Context) {
        sharePref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveCredentialUser(user: LoginResult) {
        sharePref.edit().apply {
            putString(KEY_TOKEN, user.token)
            putString(KEY_ROLE, user.role)
            putBoolean(KEY_LOGIN, true)
            apply()
        }
    }


    fun getCredentialUser(): LoginResult? {
        val role = sharePref.getString(KEY_ROLE, null)
        val token = sharePref.getString(KEY_TOKEN, null)
        return if (role != null && token != null) {
            LoginResult(role, "", "", token)
        } else {
            null
        }
    }

    fun isAdminUser(): Boolean {
        val user = getCredentialUser()
        return user?.role.toString() == "Admin"
    }

    fun setSessionTimeOut() {
        val currentTime = System.currentTimeMillis()
        sharePref.edit().apply {
            putLong(SESSION_TIMEOUT, currentTime + TIMEOUT_DURATION)
            apply()
        }
    }

    fun isSessionExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val sessionTimeout = sharePref.getLong(SESSION_TIMEOUT, 0)
        return currentTime > sessionTimeout
    }

    fun isUserLogin(): Boolean {
        return sharePref.getBoolean(KEY_LOGIN, false)
    }

    fun clearCredentialUser() {
        sharePref.edit().clear().apply()
    }

    companion object {
        private const val PREF_NAME = "user_pref"
        private const val KEY_TOKEN = "token"
        private const val KEY_ROLE = "role"
        private const val KEY_LOGIN = "isLogin"
        private const val SESSION_TIMEOUT = "sessionTimeout"
        private const val TIMEOUT_DURATION = 300000L

        @Volatile
        private var instance: UserPreferences? = null

        fun getInstance(): UserPreferences {
            return instance ?: synchronized(this) {
                instance ?: UserPreferences().also { instance = it }
            }
        }
    }


}