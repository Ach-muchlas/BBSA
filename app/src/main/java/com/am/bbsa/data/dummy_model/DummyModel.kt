package com.am.bbsa.data.dummy_model

sealed class DummyModel {
    data class DataCard(
        val id : Int,
        val title: Int,
        val icon: Int
    )

    data class DataNews(
        val title : String,
        val desc: String,
        val image: Int
    )

    data class DummyModelTypeWaste(
        val image: Int,
        val title: String,
        val category: String,
        val price: String
    )

    data class DataBank(
        val id : Int,
        val nameBank: String,
        val bankCode: String
    )
}
