package com.example.postproyects.ui.viewmodel.acceso_registro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postproyects.data.model.ApiError
import com.example.postproyects.data.model.ApiResponse
import com.example.postproyects.data.model.users.UserRegister
import com.example.postproyects.domain.restfulll.post.PostRegisterUserUseCase
import com.example.postproyects.utils.isNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroUserViewModel @Inject constructor(
    private val postRegisterUserUseCase: PostRegisterUserUseCase,
): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message
    private val _action = MutableLiveData<Boolean>()
    val action : LiveData<Boolean> = _action
    private lateinit var error : ApiError
    private lateinit var data : ApiResponse
    fun register(correo:String,pass:String){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = postRegisterUserUseCase(UserRegister(correo,pass))
            if(!result.isNull()){
                try {
                    data = result as ApiResponse
                    _message.postValue(data.mensaje)
                    _action.postValue(data.code)
                }catch (ex:Exception){
                    error = result as ApiError
                    _message.postValue(error.mensaje)
                    when (error.code) {
                        401 -> {}
                        404 -> {}
                    }
                }
            }else{
                //_stateData.postValue(false)
            }
            _isLoading.postValue(false)
        }
    }
}