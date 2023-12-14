package com.mhmdnurulkarim.hukumq.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Utils {
    val BASE_URL = "https://api-berita-indonesia.vercel.app/"

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }
}