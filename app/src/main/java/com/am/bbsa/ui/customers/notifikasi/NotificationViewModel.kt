package com.am.bbsa.ui.customers.notifikasi

import androidx.lifecycle.ViewModel
import com.am.bbsa.service.source.Repository

class NotificationViewModel(private val repository: Repository) : ViewModel() {
    fun showNotification(token: String) = repository.getDataNotification(token)
}