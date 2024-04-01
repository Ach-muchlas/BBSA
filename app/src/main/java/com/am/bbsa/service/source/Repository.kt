package com.am.bbsa.service.source

import androidx.lifecycle.liveData
import com.am.bbsa.data.body.DepositWeighingBody
import com.am.bbsa.data.body.LoginBody
import com.am.bbsa.data.body.NewsBody
import com.am.bbsa.data.body.RegisterBody
import com.am.bbsa.data.body.SampahInformationBody
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

    fun createWasteDeposit(token: String, userId: Int, date: String, photo: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val credentials = WasteDepositBody(userId, date, photo)
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

    companion object {
        const val MESSAGE = "message"
    }
}