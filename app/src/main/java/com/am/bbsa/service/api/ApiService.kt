package com.am.bbsa.service.api

import com.am.bbsa.data.body.DepositWeighingBody
import com.am.bbsa.data.body.LoginBody
import com.am.bbsa.data.body.NewsBody
import com.am.bbsa.data.body.RegisterBody
import com.am.bbsa.data.body.SampahInformationBody
import com.am.bbsa.data.body.WasteDepositBody
import com.am.bbsa.data.response.DepositWeighingResponse
import com.am.bbsa.data.response.DetailNasabahResponse
import com.am.bbsa.data.response.GeneralResponse
import com.am.bbsa.data.response.HistoryDepositResponse
import com.am.bbsa.data.response.LoginResponse
import com.am.bbsa.data.response.NasabahResponse
import com.am.bbsa.data.response.NewsResponse
import com.am.bbsa.data.response.NotificationResponse
import com.am.bbsa.data.response.RegisterResponse
import com.am.bbsa.data.response.SampahResponse
import com.am.bbsa.data.response.TotalSaldoResponse
import com.am.bbsa.data.response.UserResponse
import com.am.bbsa.data.response.WasteDepositResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    /*digunakan untuk login user(admin/nasabah)*/
    @POST("login")
    suspend fun login(
        @Body loginBody: LoginBody
    ): Response<LoginResponse>

    /*digunakan untuk register dari user(admin/nasabah)*/
    @POST("register")
    suspend fun register(
        @Body registerBody: RegisterBody
    ): Response<RegisterResponse>

    /*digunakan untuk menampilkan list nasabah*/
    @GET("nasabah")
    suspend fun getAllNasabah(
        @Header("Authorization") bearer: String
    ): Response<NasabahResponse>

    /*digunakan untuk mengambil detail nasabah*/
    @GET("nasabah/detail/{id}")
    suspend fun getDetailNasabah(
        @Path("id") id: Int,
        @Header("Authorization") bearer: String
    ): Response<DetailNasabahResponse>

    @GET("nasabah/total-saldo")
    suspend fun getTotalBalance(
        @Header("Authorization") bearer: String
    ): Response<TotalSaldoResponse>

    /*digunakan untuk mengambil informasi sampah*/
    @GET("sampah")
    suspend fun getAllWaste(
        @Header("Authorization") bearer: String
    ): Response<SampahResponse>

    /*digunakan untuk menambah data sampah*/
    @POST("sampah/create")
    suspend fun createWaste(
        @Body sampahInformationBody: SampahInformationBody,
        @Header("Authorization") bearer: String
    ): Response<GeneralResponse>

    /*digunakan untuk update data sampah*/
    @POST("sampah/edit/{id}")
    suspend fun updateWaste(
        @Path("id") id: Int,
        @Body sampahInformationBody: SampahInformationBody,
        @Header("Authorization") bearer: String
    ): Response<WasteDepositResponse>

    /*digunakan untuk delete data sampah*/
    @DELETE("sampah/delete/{id}")
    suspend fun deleteWaste(
        @Path("id") id: Int,
        @Header("Authorization") bearer: String
    ): Response<GeneralResponse>

    @GET("setor-sampah")
    suspend fun getAllWasteDeposit(
        @Header("Authorization") bearer: String
    ): Response<WasteDepositResponse>

    /*digunakan untuk menambah setoran sampah*/
    @POST("setor-sampah/create")
    suspend fun createDepositWaste(
        @Body wasteDepositBody: WasteDepositBody,
        @Header("Authorization") bearer: String
    ): Response<GeneralResponse>

    @GET("riwayat-setoran")
    suspend fun getHistoryDeposit(
        @Header("Authorization") bearer: String
    ): Response<HistoryDepositResponse>

    @GET("penimbangan-setoran")
    suspend fun getDepositWeighing(
        @Header("Authorization") bearer: String
    ): Response<DepositWeighingResponse>

    @PUT("penimbangan-setoran/update")
    suspend fun updatedDepositWeighing(
        @Header("Authorization") bearer: String,
        @Body depositWeighing: DepositWeighingBody
    ): Response<GeneralResponse>

    @GET("berita")
    suspend fun getNews(
        @Header("Authorization") bearer: String,
    ): Response<NewsResponse>

    @POST("berita/create")
    suspend fun createNews(
        @Header("Authorization") bearer: String,
        @Body newsBody: NewsBody
    ): Response<GeneralResponse>

    @GET("get-data-user")
    suspend fun getDataUser(
        @Header("Authorization") bearer: String,
    ): Response<UserResponse>


    @GET("get-data-notifikasi")
    suspend fun getNotification(
        @Header("Authorization") bearer : String
    ) : Response<NotificationResponse>
}
