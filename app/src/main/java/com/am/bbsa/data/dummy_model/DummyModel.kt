package com.am.bbsa.data.dummy_model

import android.provider.ContactsContract.CommonDataKinds.StructuredName

sealed class DummyModel {
    data class DataCard(
        val id : Int,
        val title: Int,
        val icon: Int
    )

    data class DataNews(
        val title : String,
        val desc : String,
        val image : Int
    )

    data class DummyModelTypeWaste(
        val image: Int,
        val title : String,
        val category :String,
        val price : String
    )
}
