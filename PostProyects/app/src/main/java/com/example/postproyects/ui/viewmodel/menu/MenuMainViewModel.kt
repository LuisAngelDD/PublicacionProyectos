package com.example.postproyects.ui.viewmodel.menu

import androidx.lifecycle.*
import com.example.postproyects.data.model.ApiError
import com.example.postproyects.data.model.proyects.*
import com.example.postproyects.domain.restfulll.get.*
import com.example.postproyects.utils.isNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuMainViewModel @Inject constructor(
    private val getStatusUseCase: GetStatusUseCase,
    private val getTypeUseCase: GetTypeUseCase,
    private val getSearchPrUseCase: GetSearchPrUseCase,
    private val getMyFollowsUseCase: GetMyFollowsUseCase,
    private val getMyProyectsUseCase: GetMyProyectsUseCase,
    private val getFollowsOfProyectsUseCase: GetFollowsOfProyectsUseCase,
    private val getLikesOfProyectsUseCase: GetLikesOfProyectsUseCase
) : ViewModel() {
    private val _status = MutableLiveData<List<StatusProyects>>()
    private val _type = MutableLiveData<List<TypeProyects>>()
    private val _pr = mutableListOf<Pr>()
    private val _my_fl_pr = mutableListOf<Count>()
    private val _my_lk_pr = mutableListOf<Count>()
    private val _pr_card = MutableLiveData<List<PrData>>()
    private var codes : String = ""
    private val _answer = MutableLiveData<Boolean>()
    private val _ui = MutableLiveData<Int>()
    val pr_card :LiveData<List<PrData>> = _pr_card
    val status: LiveData<List<StatusProyects>> = _status
    val type: LiveData<List<TypeProyects>> = _type
    val answer: LiveData<Boolean> = _answer
    val ui:LiveData<Int> = _ui
    fun loadType() {
        viewModelScope.launch {
            val result = getTypeUseCase()
            if(!result.isNull()){
                try {
                    _type.postValue(result as List<TypeProyects>)
                }catch (ex:Exception){
                    try {
                        result as ApiError
                        _type.postValue(emptyList())
                    }catch (ex:Exception){
                        result as String
                    }
                }
            }
        }
    }
    fun loadStatus() {
        viewModelScope.launch {
            val result = getStatusUseCase()
            if(!result.isNull()){
                try {
                    _status.postValue(result as List<StatusProyects>)
                }catch (er:Exception){
                    try {
                        result as ApiError
                        _status.postValue(emptyList())
                    }catch (ex:Exception){
                        result as String
                        _answer.postValue(false)
                    }
                }
            }
        }
    }
    fun searchProyects(status: String,type: String,nombre:String) {
        viewModelScope.launch {
            _answer.postValue(true)
            _pr.clear()
            _my_fl_pr.clear()
            _my_lk_pr.clear()
            val resultPr = getSearchPrUseCase(status,type,nombre)
            if(!resultPr.isNull()){
                try {
                    _pr.addAll(resultPr as List<Pr>)
                    var cod = ""
                    for (data in _pr){
                        cod = cod+data.id+","
                    }
                    setCodes(cod)
                    val resultFl = getFollowsOfProyectsUseCase(codes)
                    if(!resultFl.isNull()){
                        try {
                            _my_fl_pr.addAll(resultFl as List<Count>)
                        }catch (ex:Exception){
                            try{
                                resultFl as ApiError
                                _my_fl_pr.clear()
                                _ui.postValue(1)
                                _answer.postValue(false)
                            }catch (er:Exception){
                                resultFl as String
                                _my_fl_pr.clear()
                                _ui.postValue(2)
                                _answer.postValue(false)
                            }
                        }
                    } else { _answer.postValue(false) }
                    val resultLk = getLikesOfProyectsUseCase(codes)
                    if(!resultLk.isNull()){
                        try {
                            _my_lk_pr.addAll(resultLk as List<Count>)
                        }catch (ex:Exception){
                            try{
                                resultLk as ApiError
                                _my_lk_pr.clear()
                                _ui.postValue(1)
                                _answer.postValue(false)
                            }catch (er:Exception){
                                resultLk as String
                                _my_lk_pr.clear()
                                _ui.postValue(2)
                                _answer.postValue(false)
                            }
                        }
                    } else { _answer.postValue(false)}
                    var new = arrayListOf<PrData>()
                    for ((i, a) in _pr.withIndex()) {
                        new.add(
                            PrData(a.id, a.nombre, a.author, a.desc, a.tipo, a.estado,
                                try{ _my_fl_pr[i].count }catch(ex:Exception){ "0" },
                                try{ _my_lk_pr[i].count }catch(ex:Exception){ "0" }
                            )
                        )
                    }
                    _pr_card.postValue(new as List<PrData>)
                    _answer.postValue(false)
                    _ui.postValue(0)
                }catch (ex:Exception){
                    try{
                        resultPr as ApiError
                        _pr.clear()
                        _answer.postValue(false)
                        _ui.postValue(1)
                    }catch (er:Exception){
                        resultPr as String
                        _pr.clear()
                        _answer.postValue(false)
                        _ui.postValue(2)
                    }
                }
            }
            else { _answer.postValue(false) }
        }
    }
    fun searchMyProyects(token:String,status: String,type: String,nombre:String) {
        viewModelScope.launch {
            _answer.postValue(true)
            _pr.clear()
            _my_fl_pr.clear()
            _my_lk_pr.clear()
            val resultPr = getMyProyectsUseCase(token,status,type,nombre)
            if(!resultPr.isNull()){
                try {
                    _pr.addAll(resultPr as List<Pr>)
                    var cod = ""
                    for (data in _pr){
                        cod = cod+data.id+","
                    }
                    setCodes(cod)
                    val resultFl = getFollowsOfProyectsUseCase(codes)
                    if(!resultFl.isNull()){
                        try {
                            _my_fl_pr.addAll(resultFl as List<Count>)
                        }catch (ex:Exception){
                            try{
                                resultFl as ApiError
                                _my_fl_pr.clear()
                                _ui.postValue(1)
                                _answer.postValue(false)
                            }catch (er:Exception){
                                resultFl as String
                                _my_fl_pr.clear()
                                _ui.postValue(2)
                                _answer.postValue(false)
                            }
                        }
                    } else { _answer.postValue(false) }
                    val resultLk = getLikesOfProyectsUseCase(codes)
                    if(!resultLk.isNull()){
                        try {
                            _my_lk_pr.addAll(resultLk as List<Count>)
                        }catch (ex:Exception){
                            try{
                                resultLk as ApiError
                                _my_lk_pr.clear()
                                _ui.postValue(1)
                                _answer.postValue(false)
                            }catch (er:Exception){
                                resultLk as String
                                _my_lk_pr.clear()
                                _ui.postValue(2)
                                _answer.postValue(false)
                            }
                        }
                    } else { _answer.postValue(false)}
                    var new = arrayListOf<PrData>()
                    for ((i, a) in _pr.withIndex()) {
                        new.add(
                            PrData(a.id, a.nombre, a.author, a.desc, a.tipo, a.estado,
                                try{ _my_fl_pr[i].count }catch(ex:Exception){ "0" },
                                try{ _my_lk_pr[i].count }catch(ex:Exception){ "0" }
                            )
                        )
                    }
                    _pr_card.postValue(new as List<PrData>)
                    _answer.postValue(false)
                    _ui.postValue(0)
                }catch (ex:Exception){
                    try{
                        resultPr as ApiError
                        _pr.clear()
                        _answer.postValue(false)
                        _ui.postValue(1)
                    }catch (er:Exception){
                        resultPr as String
                        _pr.clear()
                        _answer.postValue(false)
                        _ui.postValue(2)
                    }
                }
            }
            else { _answer.postValue(false) }
        }
    }
    fun searchMyFllProyects(token:String,status: String,type: String,nombre:String) {
        viewModelScope.launch {
            _answer.postValue(true)
            _pr.clear()
            _my_fl_pr.clear()
            _my_lk_pr.clear()
            val resultPr = getMyFollowsUseCase(token,status,type,nombre)
            if(!resultPr.isNull()){
                try {
                    _pr.addAll(resultPr as List<Pr>)
                    var cod = ""
                    for (data in _pr){
                        cod = cod+data.id+","
                    }
                    setCodes(cod)
                    val resultFl = getFollowsOfProyectsUseCase(codes)
                    if(!resultFl.isNull()){
                        try {
                            _my_fl_pr.addAll(resultFl as List<Count>)
                        }catch (ex:Exception){
                            try{
                                resultFl as ApiError
                                _my_fl_pr.clear()
                                _ui.postValue(1)
                                _answer.postValue(false)
                            }catch (er:Exception){
                                resultFl as String
                                _my_fl_pr.clear()
                                _ui.postValue(2)
                                _answer.postValue(false)
                            }
                        }
                    } else { _answer.postValue(false) }
                    val resultLk = getLikesOfProyectsUseCase(codes)
                    if(!resultLk.isNull()){
                        try {
                            _my_lk_pr.addAll(resultLk as List<Count>)
                        }catch (ex:Exception){
                            try{
                                resultLk as ApiError
                                _my_lk_pr.clear()
                                _ui.postValue(1)
                                _answer.postValue(false)
                            }catch (er:Exception){
                                resultLk as String
                                _my_lk_pr.clear()
                                _ui.postValue(2)
                                _answer.postValue(false)
                            }
                        }
                    } else { _answer.postValue(false)}
                    var new = arrayListOf<PrData>()
                    for ((i, a) in _pr.withIndex()) {
                        new.add(
                            PrData(a.id, a.nombre, a.author, a.desc, a.tipo, a.estado,
                                try{ _my_fl_pr[i].count }catch(ex:Exception){ "0" },
                                try{ _my_lk_pr[i].count }catch(ex:Exception){ "0" }
                            )
                        )
                    }
                    _pr_card.postValue(new as List<PrData>)
                    _answer.postValue(false)
                    _ui.postValue(0)
                }catch (ex:Exception){
                    try{
                        resultPr as ApiError
                        _pr.clear()
                        _answer.postValue(false)
                        _ui.postValue(1)
                    }catch (er:Exception){
                        resultPr as String
                        _pr.clear()
                        _answer.postValue(false)
                        _ui.postValue(2)
                    }
                }
            }
            else { _answer.postValue(false) }
        }
    }
    fun setCodes(code:String){
        codes = ""
        codes = code
    }
    fun loadFollows(){
        _my_fl_pr.clear()
        viewModelScope.launch {
            val resultFl = getFollowsOfProyectsUseCase(codes)
            if(!resultFl.isNull()){
                try {
                    _my_fl_pr.addAll(resultFl as List<Count>)
                }catch (ex:Exception){
                    try{
                        resultFl as ApiError
                        _my_fl_pr.clear()
                        _ui.postValue(1)
                        _answer.postValue(false)
                    }catch (er:Exception){
                        resultFl as String
                        _my_fl_pr.clear()
                        _ui.postValue(2)
                        _answer.postValue(false)
                    }
                }
            } else { _answer.postValue(false) }
        }
    }
    fun loadLikes(){
        _my_lk_pr.clear()
        viewModelScope.launch {
            val resultLk = getLikesOfProyectsUseCase(codes)
            if(!resultLk.isNull()){
                try {
                    _my_lk_pr.addAll(resultLk as List<Count>)
                }catch (ex:Exception){
                    try{
                        resultLk as ApiError
                        _my_lk_pr.clear()
                        _ui.postValue(1)
                        _answer.postValue(false)
                    }catch (er:Exception){
                        resultLk as String
                        _my_lk_pr.clear()
                        _ui.postValue(2)
                        _answer.postValue(false)
                    }
                }
            } else { _answer.postValue(false)}
        }
    }
    fun exit(){
        codes = ""
        _pr.clear()
        _my_fl_pr.clear()
        _my_lk_pr.clear()
    }
}