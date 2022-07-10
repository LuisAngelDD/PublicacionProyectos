package com.example.postproyects.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postproyects.data.model.ApiError
import com.example.postproyects.data.model.ApiResponse
import com.example.postproyects.data.model.proyects.PostProyect
import com.example.postproyects.data.model.proyects.UpdateProyect
import com.example.postproyects.domain.restfulll.post.PostProyectUseCase
import com.example.postproyects.domain.restfulll.put.PutProyectUseCase
import com.example.postproyects.utils.isNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostProyectViewModel @Inject constructor(
    private val postProyectUseCase: PostProyectUseCase,
    private val updateProyectUseCase: PutProyectUseCase
): ViewModel() {
    private val _answerCheck = MutableLiveData<Boolean>()
    val answerCheck : LiveData<Boolean> = _answerCheck
    private val _pr = MutableLiveData<Boolean>(false)
    val pr : LiveData<Boolean> = _pr
    fun post(token:String,nombre:String,desc:String,type:String){
        viewModelScope.launch {
            _pr.postValue(true)
            delay(3000L)
            val post = postProyectUseCase(token, PostProyect(nombre,desc,type))
            if (!post.isNull()){
                try {
                    post as ApiResponse
                    _pr.postValue(false)
                    _answerCheck.postValue(true)
                }catch (ex:Exception){
                    post as ApiError
                    _pr.postValue(false)
                    _answerCheck.postValue(false)
                }
            }
            _pr.postValue(false)
        }
    }
    fun put(id:String,nombre:String,desc:String,status:String){
        viewModelScope.launch {
            _pr.postValue(true)
            delay(3000L)
            val put = updateProyectUseCase(id, UpdateProyect(nombre,desc,status))
            if (!put.isNull()){
                try {
                    put as ApiResponse
                    _pr.postValue(false)
                    _answerCheck.postValue(true)
                }catch (ex:Exception){
                    try{
                        put as ApiError
                        _pr.postValue(false)
                        _answerCheck.postValue(false)
                    }catch (er:Exception){
                        _pr.postValue(false)
                        _answerCheck.postValue(false)
                    }
                }
            }
            _pr.postValue(false)
        }
    }
}