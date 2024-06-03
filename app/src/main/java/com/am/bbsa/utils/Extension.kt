package com.am.bbsa.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.am.bbsa.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
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

fun EditText.textChanges() : Flow<CharSequence?>{
    return callbackFlow {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s).isSuccess
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        addTextChangedListener(watcher)
        awaitClose { removeTextChangedListener(watcher) }
    }.conflate()
}
