package com.am.bbsa.ui.customers.home

import androidx.lifecycle.ViewModel
import com.am.bbsa.service.source.Repository

class HomeViewModel(private val repository: Repository) : ViewModel() {
    fun showDataUser(token: String) = repository.getDataUser(token)

    fun showNews(token: String) =repository.getALlNews(token)
}