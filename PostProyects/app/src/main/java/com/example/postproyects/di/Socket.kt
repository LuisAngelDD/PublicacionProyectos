package com.example.postproyects.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Socket {
    @Singleton
    @Provides
    @Synchronized
    fun setSocket(): Any? {
        return try{
            IO.socket("https://app-sock-p.herokuapp.com/")
        }catch (e: URISyntaxException) {
            null
        }
    }
    @Singleton
    @Provides
    @Synchronized
    fun establishConnection(mSocket:Socket):Socket {
        return mSocket.connect()
    }
    @Singleton
    @Provides
    @Synchronized
    fun closeConnection(mSocket:Socket):Socket {
        return mSocket.disconnect()
    }
}