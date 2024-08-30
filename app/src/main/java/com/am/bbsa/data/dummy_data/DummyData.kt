package com.am.bbsa.data.dummy_data

import com.am.bbsa.R
import com.am.bbsa.data.dummy_model.DummyModel

object DummyData {
    val DummyDataCardAccount = arrayListOf(
        DummyModel.DataCard(1, R.string.my_profile, R.drawable.icon_my_profile),
        DummyModel.DataCard(2, R.string.change_password, R.drawable.icon_secure),
        DummyModel.DataCard(3, R.string.logout, R.drawable.icon_logout),
    )

    val DummyDataCardAccountAdmin = arrayListOf(
        DummyModel.DataCard(1, R.string.my_profile, R.drawable.icon_my_profile),
        DummyModel.DataCard(2, R.string.change_password, R.drawable.icon_secure),
        DummyModel.DataCard(3, R.string.create_admin, R.drawable.icon_add_admin),
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
    )


    val DataListBank = arrayListOf(
        DummyModel.DataBank(1, "Bank Negara Indonesia (BNI)", "BNI"),
        DummyModel.DataBank(2, "Bank Central Asia (BCA)", "BCA"),
        DummyModel.DataBank(3, "Bank Rakyat Indonesia (BRI)", "BRI"),
        DummyModel.DataBank(4, "Bank Syariah Indonesia", "BSI"),
        DummyModel.DataBank(5, "Bank Tabungan Negara (BTN)", "BTN"),
        DummyModel.DataBank(6, "Bank Jago", "JAGO"),
        DummyModel.DataBank(7, "ShoppePay", "SHOPEEPAY"),
        DummyModel.DataBank(8, "Dana", "DANA"),
        DummyModel.DataBank(9, "Gopay", "GOPAY"),
        DummyModel.DataBank(10, "OVO", "OVO"),
    )
}