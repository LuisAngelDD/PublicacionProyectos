package com.example.postproyects.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postproyects.data.model.proyects.Pr
import com.example.postproyects.di.Socket
import kotlinx.coroutines.launch
import org.json.JSONObject


class SocketViewModel:ViewModel() {
    private val _socket = MutableLiveData<io.socket.client.Socket?>(null)
    val socket : LiveData<io.socket.client.Socket?> = _socket
    private val _count_follows= MutableLiveData<JSONObject?>()
    val count_follows : LiveData<JSONObject?> = _count_follows
    private val _count_likes= MutableLiveData<JSONObject?>()
    val count_likes : LiveData<JSONObject?> = _count_likes
    private val _dataPr = MutableLiveData<List<Pr>>()
    val dataPr : LiveData<List<Pr>> = _dataPr
    private val _txt = MutableLiveData<String?>(null)
    val txt : LiveData<String?> = _txt
    private val _opt = MutableLiveData<Boolean>(false)
    val opt : LiveData<Boolean> = _opt

    fun setConnection(){
        viewModelScope.launch {
            try {
                _socket.postValue(Socket.establishConnection(Socket.setSocket() as io.socket.client.Socket))
            }catch (ex:Exception) {
                _socket.postValue(null)
            }
        }
    }
    fun searchUpdatesFollows(code:String){
        socket.value?.emit("client:follows:update",code)
    }
    fun searchUpdatesLikes(code:String){
        socket.value?.emit("client:likes:update",code)
    }
    fun newProyects(){
        socket.value?.emit("client:new:proyectos")
    }
    fun updateDataProyects(){
        socket.value?.emit("client:update:proyectos")
    }
    fun updateFollows(){
        socket.value?.on("server:myFollows:update") { data ->
            when {
                data[0] != null -> {
                    _count_follows.postValue(data[0] as JSONObject)
                }
            }
        }
    }
    fun updateLikes(){
        socket.value?.on("server:likes:update") { data ->
            when {
                data[0] != null -> {
                    _count_likes.postValue(data[0] as JSONObject)
                }
            }
        }
    }
    fun notificationProyects(){
        socket.value?.on("server:new:proyectos") { data ->
            when {
                data[0] != null -> {
                    _txt.postValue(data[0] as String)
                }
            }
        }
    }
    fun updateProyect(){
        socket.value?.on("server:update:proyectos") { data ->
            when {
                data[0] != null -> {
                    _opt.postValue(true)
                }
            }
        }
    }
    fun exit(){
        _count_follows.postValue(null)
        _count_likes.postValue(null)
        _txt.postValue(null)
        _opt.postValue(false)
    }
    fun closeConnection(){
        viewModelScope.launch {
            Socket.closeConnection(socket.value!!)
            _socket.postValue(null)
        }
    }
}