package com.am.bbsa.ui.admin.menu

import androidx.lifecycle.ViewModel
import com.am.bbsa.service.source.Repository

class MenuViewModel(private val repository: Repository) : ViewModel() {
    fun showAllNasabah(token: String) = repository.getAllNasabah(token)

    /*change data nasabah in admin*/
    fun changeNameNasabah(token: String, nasabahId: Int, name: String) =
        repository.changeNameNasabah(token, nasabahId, name)

    fun changePhotoProfileNasabah(token: String, nasabahId: Int, urlPhotoProfile: String) =
        repository.changePhotoProfileNasabah(token, nasabahId, urlPhotoProfile)

    fun changeNIKNasabah(token: String, nasabahId: Int, NIK: String) =
        repository.changeNIKNasabah(token, nasabahId, NIK)

    fun changePhoneNumberNasabah(token: String, nasabahId: Int, phoneNumber: String) =
        repository.changePhoneNumberNasabah(token, nasabahId, phoneNumber)

    fun changeAddressNasabah(token: String, nasabahId: Int, address: String) =
        repository.changeAddressNasabah(token, nasabahId, address)

    fun changeNoRegisterNasabah(token: String, nasabahId: Int, noRegister: String) =
        repository.changeNoRegisterNasabah(token, nasabahId, noRegister)
    /**/
    fun showDetailNasabahById(id: Int, token: String) = repository.getDetailNasabah(id, token)
    fun searchNasabahByName(token: String, name: String) = repository.searchNasabah(token, name)
    fun showAllInformationWaste(token: String) = repository.getAllWaste(token)
    fun createInformationWaste(
        token: String, name: String, type: String, price: Int, imageUrl: String
    ) = repository.createInformationWaste(name, type, price, imageUrl, token)

    fun updateInformationWaste(
        id: Int,
        token: String,
        name: String,
        type: String,
        price: Int,
        imageUrl: String
    ) = repository.updateInformationWaste(name, type, price, imageUrl, token, id)

    fun deleteInformationWaste(id: Int, token: String) =
        repository.deleteInformationWaste(id, token)

    fun showAllDepositWaste(token: String) = repository.getAllWasteDeposit(token)
    fun createDepositWaste(token: String, photo: String) =
        repository.createWasteDeposit(token, photo)

    fun createDepositWasteAdmin(token: String, userId: Int, photo: String) =
        repository.createWasteDepositAdmin(token, userId, photo)

    fun showAllHistoryDeposit(token: String) = repository.getAllHistoryDeposit(token)
    fun showAllDepositWeighing(token: String) = repository.getDepositWeighing(token)
    fun updateDepositWeighing(
        token: String, idDepositWaste: Int, idWaste: List<Int>, wasteWeight: List<Int>
    ) = repository.updateDepositWeighing(token, idDepositWaste, idWaste, wasteWeight)

    fun showAllNews(token: String) = repository.getALlNews(token)
    fun createNews(token: String, title: String, description: String, photo: String) =
        repository.createNews(token, title, description, photo)

    fun showSchedulePickupWaste(token: String) = repository.getSchedulePickUpWaste(token)
    fun createSchedulePickupWaste(token: String, date: String) =
        repository.createSchedulePickUpWaste(token, date)

    fun showNasabahRegistrantPickupWaste(token: String) =
        repository.getNasabahRegistrantPickupWaste(token)

    fun showApprovedNasabahRegistrantPickupWaste(token: String) =
        repository.getApprovedNasabahRegistrantPickupWaste(token)

    fun changeStatusRegistrantPickupWaste(token: String, idPickupWaste: Int, status: String) =
        repository.changeStatusRegistrantPickupWaste(token, idPickupWaste, status)
}