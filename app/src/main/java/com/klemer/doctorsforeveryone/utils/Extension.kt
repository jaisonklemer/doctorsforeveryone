package com.klemer.doctorsforeveryone.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.klemer.doctorsforeveryone.R

fun FragmentActivity.replaceView(
    fragment: Fragment,
    @IdRes containerId: Int = R.id.container,
    addBackStack: Boolean = false
) {
    if(addBackStack){
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }else{
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

}

fun FragmentActivity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun checkForInternet(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        val network = connectivityManager.activeNetwork ?: return false

        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            else -> false
        }
    } else {
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}

@SuppressLint("ResourceAsColor")
 fun configSnackbar(view: View, message: String) {
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        .setTextColor(Color.WHITE)
        .setBackgroundTint(view.context.resources.getColor(R.color.greenDark))
    snackbar.anchorView = view
    snackbar.show()
}
