package com.example.postproyects.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postproyects.data.model.ApiError
import com.example.postproyects.data.model.users.UserData
import com.example.postproyects.domain.restfulll.post.PostUserDataUseCase
import com.example.postproyects.utils.isNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataUserViewModel @Inject constructor(
    private val postUserDataUseCase: PostUserDataUseCase
): ViewModel() {
    private val _dataUser = MutableLiveData<UserData?>(null)
    val dataUser : LiveData<UserData?> = _dataUser
    private lateinit var error : ApiError
    private lateinit var data : UserData
    fun getData(token:String){
        viewModelScope.launch {
            val result = postUserDataUseCase(token)
            if (!result.isNull()){
                try {
                    data = result as UserData
                    _dataUser.postValue(data)
                }catch (ex:Exception){
                    error = result as ApiError
                }
            }
        }
    }
    fun cleanData(){
        _dataUser.postValue(null)
    }
}