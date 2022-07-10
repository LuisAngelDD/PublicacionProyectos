package com.example.postproyects.utils

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConnectivityManager
@Inject
constructor(
  application: Application,
):ViewModel(){
  private val connectionLiveData = InternetConnection(application)
  val isNetworkAvailable = MutableLiveData(false)
  fun registerConnectionObserver(lifecycleOwner: LifecycleOwner){
    connectionLiveData.observe(lifecycleOwner) { isConnected ->
      isConnected?.let { isNetworkAvailable.value = it }
    }
  }
  fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner){
    connectionLiveData.removeObservers(lifecycleOwner)
  }
}