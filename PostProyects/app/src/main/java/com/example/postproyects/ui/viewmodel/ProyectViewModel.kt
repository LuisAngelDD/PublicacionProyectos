package com.example.postproyects.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postproyects.data.model.ApiError
import com.example.postproyects.data.model.ApiResponse
import com.example.postproyects.data.model.proyects.Pr
import com.example.postproyects.domain.restfulll.get.*
import com.example.postproyects.utils.isNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProyectViewModel @Inject constructor(
    private val getProyect: GetProyectUseCase,
    private val getFollowsProyectsUseCase: GetFollowsProyectsUseCase,
    private val getLikesProyectsUseCase: GetLikesProyectsUseCase,
    private val setFollowProyectUseCase: SetFollowProyectUseCase,
    private val setLikesProyectsUseCase: SetLikeProyectUseCase,
    private val getIfFollowUseCase: GetIfFollowUseCase,
    private val getIfLikeUseCase: GetIfLikeUseCase
) : ViewModel() {
    private val _find_pr = MutableLiveData<Pr?>(null)
    private val _find_follows = MutableLiveData<ApiResponse?>(null)
    private val _find_likes = MutableLiveData<ApiResponse?>(null)
    private val _if_follow = MutableLiveData<ApiResponse?>(null)
    private val _if_like = MutableLiveData<ApiResponse?>(null)
    private val _set_like = MutableLiveData(false)
    private val _set_follow = MutableLiveData(false)
    val find_pr : LiveData<Pr?> = _find_pr
    val find_follows : LiveData<ApiResponse?> = _find_follows
    val find_likes : LiveData<ApiResponse?> = _find_likes
    val if_follow : LiveData<ApiResponse?> = _if_follow
    val if_like : LiveData<ApiResponse?> = _if_like
    val set_like : LiveData<Boolean> = _set_like
    val set_follow : LiveData<Boolean> = _set_follow
    fun findProyect(cod:String){
        _find_pr.postValue(null)
        viewModelScope.launch {
            val result = getProyect(cod)
            if (!result.isNull()){
                try {
                    _find_pr.postValue(result as Pr)
                }catch (ex:Exception){
                    try{
                        result as ApiError
                        _find_pr.postValue(null)
                    }catch (ex:Exception) {
                        _find_pr.postValue(null)
                    }
                }
            }
        }
    }
    fun findFollows(cod:String){
        _find_follows.postValue(null)
        viewModelScope.launch {
            val result = getFollowsProyectsUseCase(cod)
            if (!result.isNull()){
                try {
                    _find_follows.postValue(result as ApiResponse)
                }catch (ex:Exception){
                    _find_follows.postValue(null)
                }
            }
        }
    }
    fun findLikes(cod:String){
        _find_likes.postValue(null)
        viewModelScope.launch {
            val result = getLikesProyectsUseCase(cod)
            if (!result.isNull()){
                try {
                    _find_likes.postValue(result as ApiResponse)
                }catch (ex:Exception){
                    _find_likes.postValue(null)
                }
            }
        }
    }
    fun setLike(token:String,cod:String){
        _find_likes.postValue(null)
        viewModelScope.launch {
            val result = setLikesProyectsUseCase(token,cod)
            if (!result.isNull()){
                try {
                    result as ApiResponse
                    _set_like.postValue(true)
                    //_find_likes.postValue(result)
                    _if_like.postValue(result)
                }catch (ex:Exception){
                    _find_likes.postValue(null)
                }
            }
        }
    }
    fun setFollow(token:String,cod:String){
        _find_follows.postValue(null)
        viewModelScope.launch {
            val result = setFollowProyectUseCase(token,cod)
            if (!result.isNull()){
                try {
                    result as ApiResponse
                    _set_follow.postValue(true)
                    //_find_follows.postValue(result)
                    _if_follow.postValue(result)
                }catch (ex:Exception){
                    _find_follows.postValue(null)
                }
            }
        }
    }
    fun ifFollow(token:String,cod:String){
        _if_follow.postValue(null)
        viewModelScope.launch {
            val result = getIfFollowUseCase(token,cod)
            if (!result.isNull()){
                try {
                    _if_follow.postValue(result as ApiResponse)
                }catch (ex:Exception){
                    _if_follow.postValue(null)
                }
            }
        }
    }
    fun ifLike(token:String,cod:String){
        _if_like.postValue(null)
        viewModelScope.launch {
            val result = getIfLikeUseCase(token,cod)
            if (!result.isNull()){
                try {
                    _if_like.postValue(result as ApiResponse)
                }catch (ex:Exception){
                    _if_like.postValue(null)
                }
            }
        }
    }
    fun updateLikes(data: ApiResponse){
        _find_likes.postValue(data)
    }
    fun updateFollows(data: ApiResponse){
        _find_follows.postValue(data)
    }
    fun exit(){
        _find_pr.postValue(null)
        _find_follows.postValue(null)
        _find_likes.postValue(null)
        _if_follow.postValue(null)
        _if_like.postValue(null)
        _set_like.postValue(false)
        _set_follow.postValue(false)
    }
}