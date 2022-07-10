package com.example.postproyects.di

import android.content.ContentValues.TAG
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.IOException
import java.net.InetSocketAddress
import javax.inject.Singleton
import javax.net.SocketFactory

@Module
@InstallIn(SingletonComponent::class)
object DoesNetworkHaveInternet {
  @Singleton
  @Provides
  fun execute(socketFactory: SocketFactory): Boolean {
    return try{
      Log.d(TAG, "PINGING google.")
      val socket = socketFactory.createSocket() ?: throw IOException("Socket nulo")
      socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
      socket.close()
      Log.d(TAG, "PING success.")
      true
    }catch (e: IOException){
      Log.e(TAG, "No hay coneccion a internet. ${e}")
      false
    }
  }
}