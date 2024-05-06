package com.am.bbsa.service.source

import androidx.lifecycle.liveData
import com.am.bbsa.data.body.ChangePasswordBody
import com.am.bbsa.data.body.DepositWeighingBody
import com.am.bbsa.data.body.LoginBody
import com.am.bbsa.data.body.NewsBody
import com.am.bbsa.data.body.RegisterBody
import com.am.bbsa.data.body.SampahInformationBody
import com.am.bbsa.data.body.WasteDepositAdminBody
import com.am.bbsa.data.body.WasteDepositBody
import com.am.bbsa.service.api.ApiService
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
    fun changeNoRegisterNasabah(token: String, nasabahId: Int, noRegister: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.changeNameNasabah("Bearer $token", nasabahId, noRegister)
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
    fun changeAddressNasabah(token: String, nasabahId: Int, address: String) = liveData(Dispatchers.IO) {
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

    fun createInformationWaste(
        name: String,
        type: String,
        price: Int,
        imageUrl: String,
        token: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val credentials = SampahInformationBody(name, type, price, imageUrl)
            val response = apiService.createWaste(credentials, "Bearer $token")

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

    fun updateInformationWaste(
        name: String,
        type: String,
        price: Int,
        imageUrl: String,
        token: String,
        id: Int
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val credentials = SampahInformationBody(name, type, price, imageUrl)
            val response = apiService.updateWaste(id, credentials, "Bearer $token")

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

    companion object {
        const val MESSAGE = "message"
    }
}