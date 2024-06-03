package com.am.bbsa.service.api

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
import com.am.bbsa.data.response.DepositWeighingResponse
import com.am.bbsa.data.response.DetailHistoryDepositResponse
import com.am.bbsa.data.response.DetailHistoryWithdrawBalanceResponse
import com.am.bbsa.data.response.DetailNasabahRegistrantPickupWasteResponse
import com.am.bbsa.data.response.DetailNasabahResponse
import com.am.bbsa.data.response.DetailWithdrawBalanceResponse
import com.am.bbsa.data.response.GeneralResponse
import com.am.bbsa.data.response.HistoryDepositResponse
import com.am.bbsa.data.response.HistoryDepositWasteResponse
import com.am.bbsa.data.response.HistoryRegistrantPickupWasteResponse
import com.am.bbsa.data.response.HistoryTransactionResponse
import com.am.bbsa.data.response.HistoryWithdrawBalanceResponse
import com.am.bbsa.data.response.LoginResponse
import com.am.bbsa.data.response.NasabahRegistrantPickupWasteResponse
import com.am.bbsa.data.response.NasabahResponse
import com.am.bbsa.data.response.NewsResponse
import com.am.bbsa.data.response.NotificationResponse
import com.am.bbsa.data.response.RegisterResponse
import com.am.bbsa.data.response.SampahResponse
import com.am.bbsa.data.response.SchedulePickUpWasteResponse
import com.am.bbsa.data.response.TarikSaldoResponse
import com.am.bbsa.data.response.TotalSaldoResponse
import com.am.bbsa.data.response.UpdatePriceWasteResponse
import com.am.bbsa.data.response.UserResponse
import com.am.bbsa.data.response.WasteDepositResponse
import com.am.bbsa.data.response.WithdrawBalanceResponse
import com.am.bbsa.data.response.news.DetailNewsResponse
import com.am.bbsa.data.response.report.NasabahWasteDepositReportsResponse
import com.am.bbsa.data.response.report.NasabahWithdrawBalanceReportsResponse
import com.am.bbsa.data.response.total_berat_sampah.TotalWastePerWeeksResponse
import com.am.bbsa.data.response.total_berat_sampah.TotalWasteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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

    @FormUrlEncoded
    @POST("otp-register/{id}")
    suspend fun verificationOtpRegister(
        @Path("id") id: Int,
        @Field("number_otp") number_otp: String,
    ): Response<GeneralResponse>

    @POST("create-admin")
    suspend fun createAdmin(
        @Body createAdminBody: CreateAdminBody
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("resending-otp-register")
    suspend fun resendingOtpRegister(
        @Field("nomor_telephone") phoneNumber: String,
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @POST("forgot-password")
    suspend fun forgotPassword(
        @Field("nomor_telephone") phoneNumber : String
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("otp-forgot-password/{id}")
    suspend fun verificationOtpForgotPassword(
        @Path("id") id: Int,
        @Field("number_otp") number_otp: String,
    ): Response<GeneralResponse>
    @FormUrlEncoded
    @POST("resending-otp-forgot-password")
    suspend fun resendingOtpForgotPassword(
        @Field("nomor_telephone") phoneNumber: String,
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @POST("reset-password/{id}")
    suspend fun resetPassword(
        @Path("id") id: Int,
        @Field("password") password: String,
    ): Response<GeneralResponse>

    /*digunakan untuk menampilkan list nasabah*/
    @GET("nasabah")
    suspend fun getAllNasabah(
        @Header("Authorization") bearer: String
    ): Response<NasabahResponse>

    /*digunakan untuk mengambil detail nasabah*/
    @GET("nasabah/detail/{id}")
    suspend fun getDetailNasabah(
        @Path("id") id: Int, @Header("Authorization") bearer: String
    ): Response<DetailNasabahResponse>

    @FormUrlEncoded
    @POST("nasabah/edit/{id}")
    suspend fun changeNameNasabah(
        @Header("Authorization") bearer: String,
        @Path("id") nasabahId: Int,
        @Field("name") name: String
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @POST("nasabah/edit/{id}")
    suspend fun changeNIKNasabah(
        @Header("Authorization") bearer: String,
        @Path("id") nasabahId: Int,
        @Field("NIK") NIK: String
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @POST("nasabah/edit/{id}")
    suspend fun changeGenderNasabah(
        @Header("Authorization") bearer: String,
        @Path("id") nasabahId: Int,
        @Field("jenis_kelamin") gender: String
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @POST("nasabah/edit/{id}")
    suspend fun changePhoneNumberNasabah(
        @Header("Authorization") bearer: String,
        @Path("id") nasabahId: Int,
        @Field("nomor_telephone") phoneNumber: String
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @POST("nasabah/edit/{id}")
    suspend fun changePhotoProfileNasabah(
        @Header("Authorization") bearer: String,
        @Path("id") nasabahId : Int,
        @Field("foto_profil") photoProfile : String
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @POST("nasabah/edit/{id}")
    suspend fun changeAddressNasabah(
        @Header("Authorization") bearer: String,
        @Path("id") nasabahId : Int,
        @Field("alamat") address : String
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @POST("tambah-nomer-register/{id}")
    suspend fun changeNoRegisterNasabah(
        @Header("Authorization") bearer: String,
        @Path("id") nasabahId : Int,
        @Field("nomor_register") address : String
    ): Response<GeneralResponse>

    @GET("nasabah/total-saldo")
    suspend fun getTotalBalance(
        @Header("Authorization") bearer: String
    ): Response<TotalSaldoResponse>

    @FormUrlEncoded
    @POST("nasabah/search")
    suspend fun searchNasabah(
        @Header("Authorization") bearer: String,
        @Field("name") name: String
    ) : Response<NasabahResponse>

    /*digunakan untuk mengambil informasi sampah*/
    @GET("sampah")
    suspend fun getAllWaste(
        @Header("Authorization") bearer: String
    ): Response<SampahResponse>

    /*digunakan untuk menambah data sampah*/
    @POST("sampah/create")
    suspend fun createWaste(
        @Header("Authorization") bearer: String,
        @Body sampahInformationBody: SampahInformationBody
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @POST("sampah/search")
    suspend fun searchWasteByName(
        @Header("Authorization") bearer: String,
        @Field("name") name: String
    ): Response<SampahResponse>


    /*digunakan untuk update data sampah*/
    @PUT("sampah/edit/{id}")
    suspend fun updateWaste(
        @Header("Authorization") bearer: String,
        @Path("id") id: Int,
        @Body sampahInformationBody: SampahInformationBody,
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

    @POST("setor-sampah-admin/create")
    suspend fun createDepositWasteAdmin(
        @Body wasteDepositBody: WasteDepositAdminBody, @Header("Authorization") bearer: String
    ): Response<GeneralResponse>

    @GET("riwayat-setoran")
    suspend fun getHistoryDeposit(
        @Header("Authorization") bearer: String
    ): Response<HistoryDepositResponse>

    @GET("riwayat-setor-sampah")
    suspend fun getHistoryDepositWaste(
        @Header("Authorization") bearer: String
    ): Response<HistoryDepositWasteResponse>

    @GET("riwayat-setoran/detail/{id}")
    suspend fun getDetailHistoryDeposit(
        @Path("id") id: Int,
        @Header("Authorization") bearer: String,
    ): Response<DetailHistoryDepositResponse>

    @GET("penimbangan-setoran")
    suspend fun getDepositWeighing(
        @Header("Authorization") bearer: String
    ): Response<DepositWeighingResponse>

    @PUT("penimbangan-setoran/update")
    suspend fun updatedDepositWeighing(
        @Header("Authorization") bearer: String, @Body depositWeighing: DepositWeighingBody
    ): Response<GeneralResponse>

    @GET("berita")
    suspend fun getNews(
        @Header("Authorization") bearer: String,
    ): Response<NewsResponse>

    @GET("berita/detail/{id}")
    suspend fun getDetailNews(
        @Header("Authorization") bearer: String,
        @Path("id") id: Int
    ): Response<DetailNewsResponse>

    @POST("berita/create")
    suspend fun createNews(
        @Header("Authorization") bearer: String,
        @Body newsBody: NewsBody
    ): Response<GeneralResponse>

    @PUT("berita/edit/{id}")
    suspend fun updateNews(
        @Header("Authorization") bearer: String,
        @Path("id") id: Int,
        @Body newsBody: NewsBody
    ): Response<GeneralResponse>


    @DELETE("berita/delete/{id}")
    suspend fun deleteNews(
        @Header("Authorization") bearer: String,
        @Path("id") id: Int,
    ): Response<GeneralResponse>

    @GET("get-data-user")
    suspend fun getDataUser(
        @Header("Authorization") bearer: String,
    ): Response<UserResponse>


    @GET("get-data-notifikasi")
    suspend fun getNotification(
        @Header("Authorization") bearer: String
    ): Response<NotificationResponse>

    @POST("ubah-password")
    suspend fun changePassword(
        @Header("Authorization") bearer: String,
        @Body changePasswordBody: ChangePasswordBody
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @PUT("update-profil-user")
    suspend fun updateNameUser(
        @Header("Authorization") bearer: String,
        @Field("name") name: String,
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @PUT("update-profil-user")
    suspend fun updateNIKUser(
        @Header("Authorization") bearer: String,
        @Field("NIK") NIK: String,
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @PUT("update-profil-user")
    suspend fun updatePhoneNumberUser(
        @Header("Authorization") bearer: String,
        @Field("nomer_telephone") phoneNumber: String,
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @PUT("update-profil-user")
    suspend fun updateAddressUser(
        @Header("Authorization") bearer: String,
        @Field("alamat") address: String,
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @PUT("update-profil-user")
    suspend fun updatePhotoProfileUser(
        @Header("Authorization") bearer: String,
        @Field("foto_profil") photoProfile: String,
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @PUT("update-profil-user")
    suspend fun updateGenderUser(
        @Header("Authorization") bearer: String,
        @Field("jenis_kelamin") gender: String,
    ): Response<GeneralResponse>

    @GET("total-berat-sampah-all")
    suspend fun getTotalWaste(
        @Header("Authorization") bearer: String
    ): Response<TotalWasteResponse>

    @GET("total-berat-sampah-per-minggu")
    suspend fun getTotalWastePerWeeks(
        @Header("Authorization") bearer: String
    ): Response<TotalWastePerWeeksResponse>

    @PUT("sampah/validasi-harga")
    @JvmSuppressWildcards
    suspend fun updatePriceWaste(
        @Header("Authorization") bearer: String, @Body updatePrice: List<List<Int>>
    ): Response<UpdatePriceWasteResponse>

    // penjadwalan jemput sampah
    @FormUrlEncoded
    @POST("jadwal-jemput-sampah/create")
    suspend fun createSchedulePickUpWaste(
        @Header("Authorization") bearer: String,
        @Field("tanggal") date: String,
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @POST("daftar-jemput-sampah/create")
    suspend fun createRegistrantNasabahPickupWaste(
        @Header("Authorization") bearer: String,
        @Field("jadwal_jemput_sampah_id") idPickUpwaste: Int,
        @Field("deskripsi") description: String,
        @Field("foto") photo: String,
    ): Response<GeneralResponse>

    @GET("get-data-jadwal-jemput-sampah")
    suspend fun getSchedulePickUpWaste(
        @Header("Authorization") bearer: String,
    ): Response<SchedulePickUpWasteResponse>

    @GET("pendaftar-jemput-sampah")
    suspend fun getNasabahRegistrantPickupWaste(
        @Header("Authorization") bearer: String,
    ): Response<NasabahRegistrantPickupWasteResponse>

    @GET("pendaftar-jemput-sampah/detail/{id}")
    suspend fun getDetailNasabahPickupWaste(
        @Header("Authorization") bearer: String,
        @Path("id") id: Int,
    ): Response<DetailNasabahRegistrantPickupWasteResponse>

    @GET("pendaftar-jemput-sampah/approved")
    suspend fun getApprovedNasabahRegistrantPickupWaste(
        @Header("Authorization") bearer: String,
    ): Response<NasabahRegistrantPickupWasteResponse>

    @FormUrlEncoded
    @PUT("status-jemput-sampah/{id}")
    suspend fun changeStatusRegistrantPickupWaste(
        @Header("Authorization") bearer: String,
        @Path("id") id: Int,
        @Field("status") status: String
    ): Response<GeneralResponse>

    @GET("riwayat-penjemputan-sampah")
    suspend fun getHistoryRegistrantPickupWaste(
        @Header("Authorization") bearer: String,
    ): Response<HistoryRegistrantPickupWasteResponse>

    @GET("tarik-saldo")
    suspend fun getWithdrawBalance(
        @Header("Authorization") bearer: String,
    ): Response<WithdrawBalanceResponse>

    @POST("tarik-saldo/create")
    suspend fun createWithdrawBalance(
        @Header("Authorization") bearer: String, @Body withdrawBody: TarikSaldoBody
    ): Response<TarikSaldoResponse>

    @GET("tarik-saldo/detail/{external_id}")
    suspend fun getDetailWithdrawBalance(
        @Header("Authorization") bearer: String,
        @Path("external_id") external_id: String,
    ): Response<DetailWithdrawBalanceResponse>

    @GET("get-data-riwayat-transaksi")
    suspend fun getHistoryTransaction(
        @Header("Authorization") bearer: String
    ): Response<HistoryTransactionResponse>


    @GET("riwayat-tarik-saldo")
    suspend fun getHistoryWithdrawBalance(
        @Header("Authorization") bearer: String
    ): Response<HistoryWithdrawBalanceResponse>

    @GET("riwayat-tarik-saldo/detail/{id}")
    suspend fun getDetailHistoryWithdrawBalance(
        @Header("Authorization") bearer: String, @Path("id") id: Int
    ): Response<DetailHistoryWithdrawBalanceResponse>


    @GET("laporan-setor-sampah/nasabah/{id}")
    suspend fun getReportNasabahWasteDeposit(
        @Header("Authorization") bearer: String, @Path("id") id: Int
    ): Response<NasabahWasteDepositReportsResponse>

    @GET("laporan-penarikan-saldo/nasabah/{id}")
    suspend fun getReportNasabahWithdrawBalance(
        @Header("Authorization") bearer: String, @Path("id") id: Int
    ): Response<NasabahWithdrawBalanceReportsResponse>


}
