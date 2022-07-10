package com.example.postproyects.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MoveViewModel :ViewModel(){
    private val _stayFragment = MutableLiveData<Boolean>()
    val stayFragment : LiveData<Boolean> = _stayFragment
    private val _bttomNav = MutableLiveData<BottomNavigationView>()
    val bttomNav :LiveData<BottomNavigationView> =_bttomNav
    private val _navCtrl = MutableLiveData<Int>()
    val navCtrl : LiveData<Int> = _navCtrl
    private val _postProyect = MutableLiveData<Boolean>()
    val postProyect : LiveData<Boolean> = _postProyect
    private val _view_proyect = MutableLiveData<String?>(null)
    val view_proyect : LiveData<String?> = _view_proyect
    private val _edit_proyect = MutableLiveData<String?>(null)
    val edit_proyect : LiveData<String?> = _edit_proyect
    fun stay (bottomNavigationView: BottomNavigationView,navCtr:Int) {
        _bttomNav.postValue(bottomNavigationView)
        _navCtrl.postValue(navCtr)
        _stayFragment.postValue(true)
    }
    fun stayOut () {
        _stayFragment.postValue(false)
    }
    fun postProyect(){
        _postProyect.postValue(true)
    }
    fun viewProyect(code:String){
        _view_proyect.postValue(code)
    }fun editProyect(code:String){
        _edit_proyect.postValue(code)
    }
    fun toMenu(){
        _postProyect.postValue(false)
        _view_proyect.postValue(null)
        _edit_proyect.postValue(null)
    }
}