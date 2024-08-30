package com.am.bbsa.ui.customers.home

import androidx.lifecycle.ViewModel
import com.am.bbsa.service.source.Repository

class HomeViewModel(private val repository: Repository) : ViewModel() {
    fun showDataUser(token: String) = repository.getDataUser(token)
    fun showNews(token: String) = repository.getALlNews(token)
    fun showAllTypeWaste(token: String) = repository.getAllWaste(token)
    fun showSchedulePickUpWaste(token: String) = repository.getSchedulePickUpWaste(token)
    fun showHistoryRegistrantPickupWaste(token: String) =
        repository.getHistoryRegistrantPickupWaste(token)

    fun createWithdrawBalance(
        token: String,
        bankCode: String,
        accountName: String,
        accountNumber: String,
        amount: Int,
        numberOTP: Int
    ) = repository.createWithdrawBalance(
        token,
        bankCode,
        accountName,
        accountNumber,
        amount,
        numberOTP
    )
    fun sendOTPWithdrawBalance(token: String) = repository.sendOTPWithdrawBalance(token)

    fun showDetailWithdrawBalance(
        token: String, external_id: String
    ) = repository.getDetailWithdrawBalance(token, external_id)

    fun createDepositWaste(token: String, photo: String) =
        repository.createWasteDeposit(token, photo)

    fun showWithdrawBalance(token: String) = repository.getWithdrawBalance(token)

    fun showHistoryDepositWaste(token: String) = repository.getHistoryDepositWaste(token)

    fun showDetailHistoryDeposit(id: Int, token: String) =
        repository.getDetailHistoryDeposit(id, token)

    fun createRegistrantNasabahPickUpWaste(
        token: String,
        idPickUpWaste: Int,
        description: String,
        photo: String
    ) = repository.createNasabahRegistrantPickupWaste(token, idPickUpWaste, description, photo)

    fun showHistoryTransaction(token: String) = repository.getHistoryTransaction(token)

    fun showDetailHistoryWithdrawBalance(token : String, id : Int) = repository.getDetailHistoryWithdrawBalance(token, id)
}