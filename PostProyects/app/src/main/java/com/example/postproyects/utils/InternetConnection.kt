package com.example.postproyects.utils

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.postproyects.di.DoesNetworkHaveInternet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Singleton


@Singleton
class InternetConnection (context: Context) : LiveData<Boolean>() {
    val TAG = "C-Manager"
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val conectivityManagaer = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()
    private fun checkValidNetworks() {
        postValue(validNetworks.size > 0)
    }
    override fun onActive() {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
        conectivityManagaer.registerNetworkCallback(networkRequest, networkCallback)
    }
    override fun onInactive() {
        conectivityManagaer.unregisterNetworkCallback(networkCallback)
    }
    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Log.d(TAG, "Disponible: ${network}")
            val networkCapabilities = conectivityManagaer.getNetworkCapabilities(network)
            val hasInternetCapability = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
            Log.d(TAG, "Disponible: ${network}, $hasInternetCapability")
            if (hasInternetCapability == true) {
                //verificamos si la conexion actual provee internet
                CoroutineScope(Dispatchers.IO).launch {
                    val hasInternet = DoesNetworkHaveInternet.execute(network.socketFactory)
                    if(hasInternet){
                        withContext(Dispatchers.Main){
                            Log.d(TAG, "Conectando: adding network. ${network}")
                            validNetworks.add(network)
                            checkValidNetworks()
                        }
                    }
                }
            }
        }
        override fun onLost(network: Network) {
            Log.d(TAG, "No disponible: ${network}")
            validNetworks.remove(network)
            checkValidNetworks()
        }

    }

}