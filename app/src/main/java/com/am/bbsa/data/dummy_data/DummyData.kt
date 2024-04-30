package com.am.bbsa.data.dummy_data

import com.am.bbsa.R
import com.am.bbsa.data.dummy_model.DummyModel
import com.am.bbsa.utils.Formatter

object DummyData {
    val DummyDataCardAccount = arrayListOf(
        DummyModel.DataCard(1, R.string.my_profile, R.drawable.icon_my_profile),
        DummyModel.DataCard(2, R.string.change_password, R.drawable.icon_secure),
        DummyModel.DataCard(3, R.string.language, R.drawable.icon_laguange),
        DummyModel.DataCard(4, R.string.logout, R.drawable.icon_logout),
    )

    val DummyDataCardMenu = arrayListOf(
        DummyModel.DataCard(1, R.string.nasabah, R.drawable.icon_nasabah),
        DummyModel.DataCard(2, R.string.update_price_waste, R.drawable.icon_penjualan),
        DummyModel.DataCard(
            3, R.string.customer_deposit_waste, R.drawable.icon_customer_deposit_waste
        ),
        DummyModel.DataCard(4, R.string.detail_deposit_weighing, R.drawable.icon_waste_deposit),
        DummyModel.DataCard(5, R.string.history_deposit, R.drawable.icon_history),
        DummyModel.DataCard(
            6, R.string.balance_withdrawal_history, R.drawable.icon_balance_withdrawal_history
        ),
        DummyModel.DataCard(7, R.string.news, R.drawable.icon_news),
        DummyModel.DataCard(
            8,
            R.string.waste_type_information,
            R.drawable.icon_type_waste_information
        ),
        DummyModel.DataCard(9, R.string.report_customer, R.drawable.icon_report),
        DummyModel.DataCard(10, R.string.pick_up_waste, R.drawable.icon_pick_up_waste),
        DummyModel.DataCard(11, R.string.schedulling_open_warehouse, R.drawable.icon_warehouse),
        DummyModel.DataCard(12, R.string.payment_method, R.drawable.method_payment),
    )

    val DummyDataNews = arrayListOf(
        DummyModel.DataNews(
            "INDONESIA PENYUMBANG SAMPAH PLASTIK TERBESAR",
            "Jakarta (ANTARA) - Kementerian Lingkungan Hidup dan Kehutanan (KLHK) mendorong penyelesaian sampah alat peraga kampanye",
            R.drawable.example_news
        ),
        DummyModel.DataNews(
            "INDONESIA PENYUMBANG SAMPAH PLASTIK TERBESAR",
            "Jakarta (ANTARA) - Kementerian Lingkungan Hidup dan Kehutanan (KLHK) mendorong penyelesaian sampah alat peraga kampanye",
            R.drawable.example_news
        ),
        DummyModel.DataNews(
            "INDONESIA PENYUMBANG SAMPAH PLASTIK TERBESAR",
            "Jakarta (ANTARA) - Kementerian Lingkungan Hidup dan Kehutanan (KLHK) mendorong penyelesaian sampah alat peraga kampanye",
            R.drawable.example_news
        ),
        DummyModel.DataNews(
            "INDONESIA PENYUMBANG SAMPAH PLASTIK TERBESAR",
            "Jakarta (ANTARA) - Kementerian Lingkungan Hidup dan Kehutanan (KLHK) mendorong penyelesaian sampah alat peraga kampanye",
            R.drawable.example_news
        ),
    )


    val DummyDataTypeWaste = arrayListOf(
        DummyModel.DummyModelTypeWaste(
            R.drawable.example_news,
            "Kerta HVS",
            "Kertas",
            Formatter.formatCurrency(1200)
        ),
        DummyModel.DummyModelTypeWaste(
            R.drawable.example_news,
            "Buku Campur",
            "Kertas",
            Formatter.formatCurrency(1200)
        ),
        DummyModel.DummyModelTypeWaste(
            R.drawable.example_news,
            "Kerta Buram",
            "Kertas",
            Formatter.formatCurrency(400)
        ),
    )
}