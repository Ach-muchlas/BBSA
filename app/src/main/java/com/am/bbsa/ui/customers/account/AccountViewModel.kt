package com.am.bbsa.ui.customers.account

import androidx.lifecycle.ViewModel
import com.am.bbsa.service.source.Repository

class AccountViewModel(private val repository: Repository) : ViewModel() {
    fun showDataUser(token: String) = repository.getDataUser(token)
    fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String,
        repeatNewPassword: String
    ) = repository.changePassword(token, oldPassword, newPassword, repeatNewPassword)

    fun showDetailUser(id: Int, token: String) = repository.getDetailNasabah(id, token)
    fun updateNameUser(token: String, name: String) = repository.updateNameUser(token, name)
    fun updateNIKUser(token: String, nik: String) = repository.updateNIKUser(token, nik)
    fun updatePhoneNumberUser(token: String, phoneNumber: String) =
        repository.updatePhoneNumberUser(token, phoneNumber)

    fun updateAddressUser(token: String, address: String) =
        repository.updateAddressUser(token, address)

    fun updateGenderUser(token: String, gender: String) = repository.updateGenderUser(token, gender)

    fun updatePhotoProfileUser(token: String, urlPhotoProfile: String) =
        repository.updatePhotoProfileUser(token, urlPhotoProfile)

    fun createAdmin(
        name: String,
        phoneNumber: String,
        gender: String,
        address: String,
        password: String
    ) = repository.createAdmin(name, phoneNumber, gender, address, password)
}