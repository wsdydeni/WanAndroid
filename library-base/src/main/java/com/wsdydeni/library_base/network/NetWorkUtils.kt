package com.wsdydeni.library_base.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network

class NetWorkUtils {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            var isAvailable = false
            val manager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            manager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    isAvailable = true
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    isAvailable = false
                }
            })
            return isAvailable
        }
    }
}