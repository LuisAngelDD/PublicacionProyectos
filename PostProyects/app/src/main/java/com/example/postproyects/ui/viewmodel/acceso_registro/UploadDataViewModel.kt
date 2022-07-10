package com.example.postproyects.ui.viewmodel.acceso_registro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postproyects.data.model.ApiError
import com.example.postproyects.data.model.ApiResponse
import com.example.postproyects.domain.restfulll.post.PostUploadData1UseCase
import com.example.postproyects.domain.restfulll.post.PostUploadDataUseCase
import com.example.postproyects.utils.isNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
@HiltViewModel
class UploadDataViewModel @Inject constructor(
    private val postUploadDataUseCase: PostUploadDataUseCase,
    private val postUploadData1UseCase: PostUploadData1UseCase
):ViewModel() {
    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message
    private val _action = MutableLiveData<Boolean>()
    val action : LiveData<Boolean> = _action
    private val _answer = MutableLiveData<Boolean>()
    val answer: LiveData<Boolean> = _answer
    private lateinit var error : ApiError
    private lateinit var data : ApiResponse
    fun upData(token:String, image: MultipartBody.Part, userName: RequestBody, about: RequestBody,occupation: RequestBody){
        viewModelScope.launch {
            _answer.postValue(true)
            val result = postUploadDataUseCase(token,image,userName,about,occupation)
            if(!result.isNull()){
                try {
                    data = result as ApiResponse
                    _message.postValue(data.mensaje)
                    _action.postValue(true)
                    _answer.postValue(false)
                }catch (ex:Exception){
                    try {
                        error = result as ApiError
                        _message.postValue(error.mensaje)
                        _action.postValue(false)
                        _answer.postValue(false)
                        when (error.code) {
                            401 -> {}
                            404 -> {}
                        }
                    }catch (er:Exception){

                    }
                }
            }else{
                _answer.postValue(false)
            }
        }
    }
    fun upData1(token:String, userName: RequestBody,about: RequestBody,occupation: RequestBody){
        viewModelScope.launch {
            val result = postUploadData1UseCase(token,userName,about,occupation)
            if(!result.isNull()){
                try {
                    data = result as ApiResponse
                    _message.postValue(data.mensaje)
                    _action.postValue(true)
                }catch (ex:Exception){
                    try {
                        error = result as ApiError
                        _message.postValue(error.mensaje)
                        _action.postValue(false)
                        when (error.code) {
                            401 -> {}
                            404 -> {}
                        }
                    }catch (er:Exception){

                    }
                }
            }
        }
    }
}