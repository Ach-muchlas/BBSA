package com.am.bbsa.ui.admin.home

import androidx.lifecycle.ViewModel
import com.am.bbsa.service.source.Repository

class HomeAdminViewModel(private val repository: Repository) : ViewModel() {
    fun showAllActualBalance(token: String) = repository.getAllActualBalance(token)
    fun showDataUser(token: String) = repository.getDataUser(token)
    fun showTotalWaste(token: String) = repository.getTotalWaste(token)
}