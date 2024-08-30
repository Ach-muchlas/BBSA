package com.am.bbsa.service.source

import androidx.lifecycle.liveData
import com.am.bbsa.data.body.ChangePasswordBody
import com.am.bbsa.data.body.CreateAdminBody
import com.am.bbsa.data.body.DepositWeighingBody
import com.am.bbsa.data.body.LoginBody
import com.am.bbsa.data.body.NewsBody
import com.am.bbsa.data.body.RegisterBody
import com.am.bbsa.data.body.SampahInformationBody
import com.am.bbsa.data.body.TarikSaldoBody
import com.am.bbsa.data.body.WasteDepositAdminBody
import com.am.bbsa.data.body.WasteDepositBody
import com.am.bbsa.service.api.ApiService
import com.am.bbsa.utils.KEY
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject


class Repository(private val apiService: ApiService) {
    fun login(phoneNumber: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val credentials = LoginBody(phoneNumber, password)
            val response = apiService.login(credentials)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorResponse = JSONObject(it.string())
                    val jsonObject = errorResponse.getString(MESSAGE)
                    emit(Resource.error(null, jsonObject))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun register(
        name: String,
        NIK: String,
        gender: String,
        address: String,
        phoneNumber: String,
        password: String
    ) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val credentials = RegisterBody(
                    name = name,
                    NIK = NIK,
                    gender = gender,
                    address = address,
                    phoneNumber = phoneNumber,
                    password = password
                )
                val response = apiService.register(credentials)
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val jsonObject = JSONObject(it.string())
                        val errorMessage = jsonObject.getString(MESSAGE)
                        emit(Resource.error(null, errorMessage))
                    }
                }
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }


    fun createAdmin(
        name: String, phoneNumber: String, gender: String, address: String, password: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val credentials = CreateAdminBody(
                name = name,
                gender = gender,
                address = address,
                phoneNumber = phoneNumber,
                password = password
            )
            val response = apiService.createAdmin(credentials)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val jsonObject = JSONObject(it.string())
                    val errorMessage = jsonObject.getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun verificationOtpRegister(user_id: Int, number_otp: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.verificationOtpRegister(user_id, number_otp)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun verificationOtpForgotPassword(user_id: Int, number_otp: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.verificationOtpForgotPassword(user_id, number_otp)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun resendingOtpRegister(phoneNumber: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.resendingOtpRegister(phoneNumber)

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun resendingOtpForgotPassword(phoneNumber: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.resendingOtpForgotPassword(phoneNumber)

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun forgotPassword(phoneNumber: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.forgotPassword(phoneNumber)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun resetPassword(user_id: Int, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.resetPassword(user_id, password)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String,
        repeatNewPassword: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val credentials = ChangePasswordBody(oldPassword, newPassword, repeatNewPassword)
            val response = apiService.changePassword("Bearer $token", credentials)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getAllNasabah(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getAllNasabah("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val jsonObject = JSONObject(it.string())
                    val errorMessage = jsonObject.getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getNasabahFilterName(
        token: String, filterByName: String = ""
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getAllNasabah("Bearer $token")
            if (response.isSuccessful) {
                val nasabahList = response.body()?.data ?: emptyList()
                val filteredList = when (filterByName) {
                    KEY.KEY_FILTER_ASC_NAME -> nasabahList.sortedBy { it?.name?.toLowerCase() }
                    KEY.KEY_FILTER_DESC_NAME -> nasabahList.sortedByDescending { it?.name?.toLowerCase() }
                    else -> nasabahList
                }

                emit(Resource.success(filteredList))
            } else {
                response.errorBody()?.let {
                    val jsonObject = JSONObject(it.string())
                    val errorMessage = jsonObject.getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getNasabahFilterCreated(
        token: String, query_created: String = "", query_name: String = ""
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getAllNasabah("Bearer $token")
            if (response.isSuccessful) {
                var nasabahList = response.body()?.data ?: emptyList()
                nasabahList = when (query_created) {
                    KEY.KEY_FILTER_NASABAH_LAMA -> nasabahList.sortedBy { it?.createdAt }
                    KEY.KEY_FILTER_NASABAH_BARU -> nasabahList.sortedByDescending { it?.createdAt }
                    else -> nasabahList
                }
                nasabahList = when (query_name) {
                    KEY.KEY_FILTER_ASC_NAME -> nasabahList.sortedBy { it?.name?.toLowerCase() }
                    KEY.KEY_FILTER_DESC_NAME -> nasabahList.sortedByDescending { it?.name?.toLowerCase() }
                    else -> nasabahList
                }
                emit(Resource.success(nasabahList))

            } else {
                response.errorBody()?.let {
                    val jsonObject = JSONObject(it.string())
                    val errorMessage = jsonObject.getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun searchNasabah(token: String, name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.searchNasabah("Bearer $token", name)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val jsonObject = JSONObject(it.string())
                    val errorMessage = jsonObject.getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getDetailNasabah(id: Int, token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getDetailNasabah(id, "Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun changeNameNasabah(token: String, nasabahId: Int, name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.changeNameNasabah("Bearer $token", nasabahId, name)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun changeNoRegisterNasabah(token: String, nasabahId: Int, noRegister: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val response =
                    apiService.changeNoRegisterNasabah("Bearer $token", nasabahId, noRegister)
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                        emit(Resource.error(null, errorMessage))
                    }
                }
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }

    fun changeGenderNasabah(token: String, nasabahId: Int, gender: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val response = apiService.changeGenderNasabah("Bearer $token", nasabahId, gender)
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                        emit(Resource.error(null, errorMessage))
                    }
                }
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }

    fun changeAddressNasabah(token: String, nasabahId: Int, address: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val response = apiService.changeAddressNasabah("Bearer $token", nasabahId, address)
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                        emit(Resource.error(null, errorMessage))
                    }
                }
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }

    fun changePhoneNumberNasabah(token: String, nasabahId: Int, phoneNumber: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val response =
                    apiService.changePhoneNumberNasabah("Bearer $token", nasabahId, phoneNumber)
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                        emit(Resource.error(null, errorMessage))
                    }
                }
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }

    fun changeNIKNasabah(token: String, nasabahId: Int, NIK: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.changeNIKNasabah("Bearer $token", nasabahId, NIK)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun changePhotoProfileNasabah(token: String, nasabahId: Int, urlPhotoProfile: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val response = apiService.changePhotoProfileNasabah(
                    "Bearer $token",
                    nasabahId,
                    urlPhotoProfile
                )
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                        emit(Resource.error(null, errorMessage))
                    }
                }
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }

    fun updateNameUser(token: String, name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.updateNameUser("Bearer $token", name)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun updateGenderUser(token: String, gender: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.updateGenderUser("Bearer $token", gender)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun updateNIKUser(token: String, NIK: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.updateNIKUser("Bearer $token", NIK)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun updateAddressUser(token: String, address: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.updateAddressUser("Bearer $token", address)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun updatePhoneNumberUser(token: String, phoneNumber: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.updatePhoneNumberUser("Bearer $token", phoneNumber)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun updatePhotoProfileUser(token: String, photoProfile: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.updatePhotoProfileUser("Bearer $token", photoProfile)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getAllActualBalance(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getTotalBalance("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getAllWaste(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getAllWaste("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun searchWasteByName(
        token: String,
        name: String,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.searchWasteByName("Bearer $token", name)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun createInformationWaste(
        token: String,
        name: String,
        type: String,
        price: Int,
        imageUrl: String,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val credentials = SampahInformationBody(name, type, price, imageUrl)
            val response = apiService.createWaste("Bearer $token", credentials)

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun updatePriceWaste(token: String, payloadResponse: List<List<Int>>) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val response = apiService.updatePriceWaste("Bearer $token", payloadResponse)
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                        emit(Resource.error(null, errorMessage))
                    }
                }
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message.toString()))
            }
        }

    fun updateInformationWaste(
        token: String, id: Int, name: String, type: String, price: Int, imageUrl: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val credentials = SampahInformationBody(name, type, price, imageUrl)
            val response = apiService.updateWaste("Bearer $token", id, credentials)

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun deleteInformationWaste(id: Int, token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.deleteWaste(id, "Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getAllWasteDeposit(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getAllWasteDeposit("Bearer $token")

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun createWasteDeposit(token: String, photo: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val credentials = WasteDepositBody(photo)
                val response = apiService.createDepositWaste(credentials, "Bearer $token")
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                        emit(Resource.error(null, errorMessage))
                    }
                }
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }

    fun createWasteDepositAdmin(token: String, userId: Int, photo: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val credentials = WasteDepositAdminBody(userId, photo)
                val response = apiService.createDepositWasteAdmin(credentials, "Bearer $token")
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                        emit(Resource.error(null, errorMessage))
                    }
                }
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }


    fun getHistoryDepositWaste(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getHistoryDepositWaste("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getAllHistoryDeposit(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getHistoryDeposit("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getDetailHistoryDeposit(id: Int, token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getDetailHistoryDeposit(id, "Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))

            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getDepositWeighing(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getDepositWeighing("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun updateDepositWeighing(
        token: String,
        idDepositWaste: Int,
        idWaste: List<Int>,
        wasteWeight: List<Int>
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val credentials = DepositWeighingBody(idDepositWaste, idWaste, wasteWeight)
            val response = apiService.updatedDepositWeighing("Bearer $token", credentials)

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getALlNews(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getNews("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }

            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getDetailNews(token: String, id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getDetailNews("Bearer $token", id)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }

            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun createNews(token: String, title: String, description: String, photo: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val credentials = NewsBody(title, description, photo)
                val response = apiService.createNews("Bearer $token", credentials)

                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                        emit(Resource.error(null, errorMessage))
                    }
                }
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }

    fun updateNews(token: String, idNews: Int, title: String, description: String, photo: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val credentials = NewsBody(title, description, photo)
                val response = apiService.updateNews("Bearer $token", idNews, credentials)

                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                        emit(Resource.error(null, errorMessage))
                    }
                }
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }

    fun deleteNews(token: String, idNews: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.deleteNews("Bearer $token", idNews)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }


    fun getDataUser(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getDataUser("Bearer $token")

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getDataNotification(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getNotification("Bearer $token")

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getTotalWaste(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getTotalWaste("Bearer $token")

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getTotalWastePerWeeks(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getTotalWastePerWeeks("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getSchedulePickUpWaste(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getSchedulePickUpWaste("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun createSchedulePickUpWaste(token: String, date: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.createSchedulePickUpWaste("Bearer $token", date)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }


    fun getNasabahRegistrantPickupWaste(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getNasabahRegistrantPickupWaste("Bearer $token")
            if (response.isSuccessful) {
                val filteredData = response.body()?.data?.filter { it?.status == "Menunggu" }
                emit(Resource.success(filteredData))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }

        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getDetailNasabahPickupWaste(token: String, id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getDetailNasabahPickupWaste("Bearer $token", id)

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun createNasabahRegistrantPickupWaste(
        token: String, idPickupWaste: Int, description: String, photo: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.createRegistrantNasabahPickupWaste(
                "Bearer $token", idPickupWaste, description, photo
            )
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }

        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getHistoryRegistrantPickupWaste(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getHistoryRegistrantPickupWaste("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }

        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getApprovedNasabahRegistrantPickupWaste(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getApprovedNasabahRegistrantPickupWaste("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }

        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun changeStatusRegistrantPickupWaste(token: String, idPickupWaste: Int, status: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val response = apiService.changeStatusRegistrantPickupWaste(
                    "Bearer $token",
                    idPickupWaste,
                    status
                )

                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                        emit(Resource.error(null, errorMessage))
                    }
                }
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }

    fun sendOTPWithdrawBalance(
        token: String,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.sendOTPWithdrawalBalance("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun createWithdrawBalance(
        token: String, bankCode: String, accountName: String, accountNumber: String, amount: Int, otp: Int
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val payload = TarikSaldoBody(bankCode, accountName, accountNumber, amount, otp)
            val response = apiService.createWithdrawBalance("Bearer $token", payload)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getDetailWithdrawBalance(token: String, external_id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getDetailWithdrawBalance("Bearer $token", external_id)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }


    fun getWithdrawBalance(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getWithdrawBalance("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getHistoryTransaction(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getHistoryTransaction("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getHistoryWithdrawBalance(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getHistoryWithdrawBalance("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getDetailHistoryWithdrawBalance(token: String, id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getDetailHistoryWithdrawBalance("Bearer $token", id)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getReportDepositWasteNasabah(token: String, id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getReportNasabahWasteDeposit("Bearer $token", id)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getReportWithdrawBalanceNasabah(token: String, id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getReportNasabahWithdrawBalance("Bearer $token", id)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorMessage = JSONObject(it.string()).getString(MESSAGE)
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }


    companion object {
        const val MESSAGE = "message"
    }
}