package com.am.bbsa.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Activity.goToActivity(targetActivity: Activity, bundle: Bundle? = null) {
    val intent = Intent(this, targetActivity::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

fun FragmentActivity.replaceFragment(resId: Int, fragment: Fragment) {
    this.supportFragmentManager.beginTransaction()
        .replace(resId, fragment)
        .commit()
}