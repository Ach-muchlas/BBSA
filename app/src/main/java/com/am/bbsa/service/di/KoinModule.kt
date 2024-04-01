package com.am.bbsa.service.di

import com.am.bbsa.service.api.ApiConfig
import com.am.bbsa.service.source.Repository
import com.am.bbsa.ui.admin.home.HomeAdminViewModel
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.home.HomeViewModel
import com.am.bbsa.ui.customers.notifikasi.NotificationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {
    val databaseModule
        get() = module {
            single { ApiConfig.getApiService() }
            factory { Repository(get()) }
        }

    val uiModule
        get() = module {
            viewModel { AuthViewModel(get()) }
            viewModel { MenuViewModel(get()) }
            viewModel { HomeAdminViewModel(get()) }
            viewModel { HomeViewModel(get()) }
            viewModel { NotificationViewModel(get()) }
        }
}