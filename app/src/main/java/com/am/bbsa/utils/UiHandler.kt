package com.am.bbsa.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import com.am.bbsa.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout
import io.github.muddz.styleabletoast.StyleableToast

object UiHandler {
    fun toastSuccessMessage(context: Context, message: String) {
        StyleableToast
            .Builder(context)
            .text(message)
            .textColor(Color.WHITE)
            .backgroundColor(Color.rgb(41, 127, 102))
            .cornerRadius(16)
            .font(R.font.pop_semi_bold)
            .textSize(18F)
            .show()
    }
    fun toastErrorMessage(context: Context, message: String) {
        StyleableToast
            .Builder(context)
            .text(message)
            .textColor(Color.WHITE)
            .backgroundColor(Color.RED)
            .cornerRadius(16)
            .font(R.font.pop_semi_bold)
            .textSize(18F)
            .show()
    }

    fun setupVisibilityProgressBar(progressBar: ProgressBar, isVisible: Boolean) {
        progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun setupVisibilityBottomNavigationAdmin(activity: Activity?, isGone: Boolean) {
        val bottomNavigation =
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationAdmin)
        if (isGone) bottomNavigation?.visibility = View.GONE else bottomNavigation?.visibility =
            View.VISIBLE
    }

    fun setupVisibilityBottomNavigationNasabah(activity: Activity?, isGone: Boolean) {
        val bottomNavigation =
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        if (isGone) bottomNavigation?.visibility = View.GONE else bottomNavigation?.visibility =
            View.VISIBLE
    }

    fun manageShimmer(shimmerFrameLayout: ShimmerFrameLayout, shouldStart: Boolean) {
        if (shouldStart) {
            shimmerFrameLayout.startShimmer()
            shimmerFrameLayout.visibility = View.VISIBLE
        } else {
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
        }
    }

    fun setHintBehavior(vararg editLayout: TextInputLayout) {
        for (edl in editLayout) {
            edl.isHintEnabled = false
        }
    }

    fun validatePassword(password: String, context: Context): Boolean {
        if (password.length < 8) {
            toastErrorMessage(context, "Password harus memiliki panjang minimal 8 karakter.")
            return false
        }

        if (!password.any { it.isUpperCase() }) {
            toastErrorMessage(context, "Password harus memiliki setidaknya satu huruf besar.")
            return false
        }

        if (!password.any { it.isDigit() }) {
            toastErrorMessage(context, "Password harus memiliki setidaknya satu angka.")
            return false
        }

        val specialCharacters = "@\$!%*?&#"
        if (!password.any { it in specialCharacters }) {
            toastErrorMessage(context, "Password harus memiliki setidaknya satu karakter spesial (@\$!%*?&#).")
            return false
        }

        return true
    }
}