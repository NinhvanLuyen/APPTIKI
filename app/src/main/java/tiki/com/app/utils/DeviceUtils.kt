package tiki.com.app.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import tiki.com.app.App

/**
 * Created by ninhvanluyen on 16/11/18.
 */
object DeviceUtils {
    @SuppressLint("MissingPermission")
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = App.app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
}