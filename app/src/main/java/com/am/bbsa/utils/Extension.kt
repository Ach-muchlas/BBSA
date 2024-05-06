package com.am.bbsa.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.am.bbsa.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Activity.goToActivity(targetActivity: Activity, bundle: Bundle? = null) {
    val intent = Intent(this, targetActivity::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

fun Intent.finish(activity: Activity){
    activity.startActivity(this)
    activity.finish()
}

fun FragmentActivity.replaceFragment(resId: Int, fragment: Fragment) {
    this.supportFragmentManager.beginTransaction()
        .replace(resId, fragment)
        .commit()
}

fun Date.toISO8601String(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(this)
}
