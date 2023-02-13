package com.siddroid.gallery.core

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import javax.inject.Inject

class InternetConnectivity @Inject constructor(private val connectivityManager: ConnectivityManager) {

    fun isNetworkConnected(): Boolean {
            val n: Network? = connectivityManager.activeNetwork
            if (n != null) {
                val nc: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(n)
                return nc?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false
                        || nc?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
            }
        return false
    }
}