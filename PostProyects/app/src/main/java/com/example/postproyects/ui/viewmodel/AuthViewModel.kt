package com.example.postproyects.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postproyects.data.model.ApiError
import com.example.postproyects.data.model.users.UserAuth
import com.example.postproyects.data.model.users.UserCheckAuth
import com.example.postproyects.data.model.users.UserToken
import com.example.postproyects.domain.localdata.insertData
import com.example.postproyects.domain.restfulll.post.PostAuthCheckUseCase
import com.example.postproyects.domain.restfulll.post.PostAuthCreateUseCase
import com.example.postproyects.utils.isNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val postAuthCreateUseCase: PostAuthCreateUseCase,
    private val postAuthCheckUseCase: PostAuthCheckUseCase,
    private val insertData: insertData,
): ViewModel() {
    private val _answer = MutableLiveData<Boolean>()
    val answer : LiveData<Boolean> = _answer
    private val _answerCheck = MutableLiveData<Boolean>()
    val answerCheck : LiveData<Boolean> = _answerCheck
    private val _messageError = MutableLiveData<String>()
    val messageError : LiveData<String> = _messageError
    private val _tk = MutableLiveData<String>()
    private val tk : LiveData<String> = _tk
    private val _email = MutableLiveData<String>()
    private val email : LiveData<String> = _email
    private val _pr = MutableLiveData<Boolean>()
    val pr : LiveData<Boolean> = _pr
    private lateinit var error : ApiError
    private lateinit var data : UserToken
    fun sendCode(email:String){
        _email.postValue(email)
        sendEmail()
    }
    fun sendEmail(){
        viewModelScope.launch {
            _pr.postValue(true)
            val code = postAuthCreateUseCase(UserAuth(email.value.toString()))
            if (!code.isNull()){
                try {
                    data = code as UserToken
                    _tk.postValue(data.token)
                    _answer.postValue(true)
                }catch (ex:Exception){
                    error = code as ApiError
                    when (error.code) {
                        400 -> {_messageError.postValue(error.mensaje)}
                        404 -> {_messageError.postValue(error.mensaje)}
                    }
                }
            }
            _pr.postValue(false)
        }
    }
    fun checkCode(otp:String){
        viewModelScope.launch {
            _pr.postValue(true)
            val code = postAuthCheckUseCase(tk.value.toString(), UserCheckAuth(email.value.toString(),otp))
            if (!code.isNull()){
                try {
                    data = code as UserToken
                    insertData(data)
                    _tk.postValue("")
                    _answerCheck.postValue(true)
                }catch (ex:Exception){
                    error = code as ApiError
                    when (error.code) {
                        400,401 -> {_messageError.postValue(error.mensaje)}
                    }
                    _answerCheck.postValue(false)
                }
            }
            _pr.postValue(false)
        }
    }
    fun reset(){
        _answer.postValue(false)
    }
}