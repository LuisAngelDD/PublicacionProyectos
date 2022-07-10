package com.example.postproyects.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postproyects.data.database.entities.UserEntity
import com.example.postproyects.domain.localdata.SearchData
import com.example.postproyects.domain.localdata.clearData
import com.example.postproyects.utils.isNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LocalDataViewModel @Inject constructor(
    private val searchData: SearchData,
    private val clearData: clearData
): ViewModel() {
    private val _answer = MutableLiveData<Boolean>()
    val answer : LiveData<Boolean> = _answer
    private val _pr = MutableLiveData<Boolean>()
    val pr : LiveData<Boolean> = _pr
    private val _data = MutableLiveData<String?>(null)
    val data : LiveData<String?> = _data

    fun search(){
        viewModelScope.launch {
            _pr.postValue(true)
            delay(3000L)
            val data = searchData()
            if (!data.isNull()){
                _data.postValue(data.token)
                _answer.postValue(true)
            }else{
                _data.postValue(null)
                _pr.postValue(false)
                _answer.postValue(false)
            }
        }
    }
    fun delet(){
        viewModelScope.launch {
            clearData()
            _data.postValue(null)
        }
    }
}