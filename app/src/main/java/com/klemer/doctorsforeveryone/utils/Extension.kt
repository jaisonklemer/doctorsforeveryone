package com.klemer.doctorsforeveryone.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Binder
import android.os.Build
import android.renderscript.ScriptGroup
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.ActivityMainBinding
import com.klemer.doctorsforeveryone.view.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun FragmentActivity.replaceView(
    fragment: Fragment,
    @IdRes containerId: Int = R.id.container,
    addBackStack: Boolean = false
) {
    if (addBackStack) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    } else {
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
fun configSnackbar(
    view: View,
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    action: String? = "Fechar",
    actionCallback: ((Boolean) -> Unit)? = null
) {
    val snackbar = Snackbar.make(view, message, duration)
        .setTextColor(Color.WHITE)
        .setBackgroundTint(view.context.resources.getColor(R.color.greenDark))

    if (action != null && actionCallback != null) {
        snackbar.setAction(action) { actionCallback(true) }
    }
    snackbar.anchorView = view
    snackbar.show()
}

fun showAlertDialog(
    context: Context,
    title: String,
    message: String,
    positiveText: String,
    negativeText: String?,
    @DrawableRes icon: Int?,
    callback: (Boolean, Boolean) -> Unit
) {

    val dialog = AlertDialog.Builder(context)
    dialog.setTitle(title)
    dialog.setMessage(message)
    dialog.setPositiveButton(positiveText) { _, _ ->
        callback(true, false)
    }

    if (negativeText != null) {
        dialog.setNegativeButton(negativeText) { _, _ ->
            callback(false, true)
        }
    }

    if (icon != null) {
        dialog.setIcon(icon)
    }

    dialog.create().show()
}

fun Context.getFirebaseError(name: String): String {
    val errorCode = name.replace("-", "_").lowercase()
    return try {
        getString(resources.getIdentifier(errorCode, "string", packageName))
    } catch (e: Exception) {
        "Ocorreu um erro. Tente novamente"
    }

}
