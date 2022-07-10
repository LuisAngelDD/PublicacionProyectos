package com.example.postproyects.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postproyects.data.model.ApiError
import com.example.postproyects.data.model.ApiResponse
import com.example.postproyects.domain.restfulll.post.PostCheckDataUseCase
import com.example.postproyects.utils.isNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CheckDataViewModel @Inject constructor(
    private val checkDataUseCase: PostCheckDataUseCase,
):ViewModel() {
    private val _answerCheck = MutableLiveData<Int>()
    val answerCheck : LiveData<Int> = _answerCheck
    private val _pr = MutableLiveData<Boolean>()
    val pr : LiveData<Boolean> = _pr
    fun checkData(token:String){
        viewModelScope.launch {
            delay(3000L)
            val check = checkDataUseCase(token)
            if (!check.isNull()){
                try {
                    check as ApiResponse
                    _answerCheck.postValue(200)
                }catch (ex:Exception){
                    val ver = check as ApiError
                    _answerCheck.postValue(ver.code)
                }
            }
            _pr.postValue(false)
        }
    }
}