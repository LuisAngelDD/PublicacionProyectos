package com.example.postproyects.ui.viewmodel.acceso_registro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postproyects.data.model.ApiError
import com.example.postproyects.data.model.users.UserLogin
import com.example.postproyects.data.model.users.UserToken
import com.example.postproyects.domain.localdata.insertData
import com.example.postproyects.domain.restfulll.post.PostLoginUserUseCase
import com.example.postproyects.utils.isNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccesoViewModel @Inject constructor(
    private val getLoginUserUseCase: PostLoginUserUseCase,
    private val insertData: insertData,
): ViewModel() {
    private val _answer = MutableLiveData<Boolean>()
    val answer : LiveData<Boolean> = _answer
    private val _answerAuth = MutableLiveData<Boolean>()
    val answerAuth : LiveData<Boolean> = _answerAuth
    private val _pr = MutableLiveData<Boolean>()
    val pr : LiveData<Boolean> = _pr
    private val _messageError = MutableLiveData<String>()
    val messageError : LiveData<String> = _messageError
    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message
    private lateinit var error : ApiError
    private lateinit var data : UserToken
    fun login (correo:String,pass:String){
        viewModelScope.launch {
            _pr.postValue(true)
            val result = getLoginUserUseCase(UserLogin(correo,pass))
            if(!result.isNull()){
                try {
                    data = result as UserToken
                    insertData(data)
                    _message.postValue(data.token)
                    _answer.postValue(true)
                }catch (ex:Exception){
                    error = result as ApiError
                    when (error.code) {
                        400, 401, 404 -> { _messageError.postValue(error.mensaje)
                            _pr.postValue(false) }
                        403 -> { _answerAuth.postValue(true) }
                    }
                }
            }else{
                _pr.postValue(false)
            }
        }
    }
}
