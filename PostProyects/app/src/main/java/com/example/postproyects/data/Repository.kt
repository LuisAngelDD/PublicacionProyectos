package com.example.postproyects.data

import com.example.postproyects.data.database.dao.UserDAO
import com.example.postproyects.data.database.entities.UserEntity
import com.example.postproyects.data.model.proyects.PostProyect
import com.example.postproyects.data.model.proyects.StatusProyects
import com.example.postproyects.data.model.proyects.TypeProyects
import com.example.postproyects.data.model.proyects.UpdateProyect
import com.example.postproyects.data.model.users.*
import com.example.postproyects.data.network.Service
import com.example.postproyects.utils.toDatabase
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Service,
    private val userDao: UserDAO,
) {
    //network-data
    //---------------------proyects-----------------------------------------------------
    suspend fun searchProyects(status: String,type: String,name:String): Any{
        val response = api.getSearchProyect(status,type,name)
        return response
    }
    suspend fun getSearchMyPr(token:String,status: String,type: String,name:String): Any{
        val response = api.getSearchMyPr(token,status,type,name)
        return response
    }
    suspend fun getSearchMyPrFll(token:String,status: String,type: String,name:String): Any{
        val response = api.getSearchMyPrFll(token,status,type,name)
        return response
    }
    suspend fun getSearchFll(codes:String): Any{
        val response = api.getSearchFll(codes)
        return response
    }
    suspend fun getSearchLK(codes:String): Any{
        val response = api.getSearchLK(codes)
        return response
    }
    suspend fun getProyect(code:String):Any{
        val response = api.getProyect(code)
        return response
    }
    suspend fun getFollowsProyect(code:String):Any{
        val response = api.getFollowsProyect(code)
        return response
    }
    suspend fun getLikesProyect(code:String):Any{
        val response = api.getLikesProyect(code)
        return response
    }
    suspend fun getIfFollowProyect(token:String,code:String):Any{
        val response = api.getIfFollowsProyect(token,code)
        return response
    }
    suspend fun getIfLikeProyect(token:String,code:String):Any{
        val response = api.getIfLikesProyect(token,code)
        return response
    }
    suspend fun setFollowProyect(token:String,code:String):Any{
        val response = api.setFollowsProyect(token,code)
        return response
    }
    suspend fun setLikeProyect(token:String,code:String):Any{
        val response = api.setLikesProyect(token,code)
        return response
    }
    //-----------------------Status-of-proyects-----------------------------------------
    suspend fun getStatus(): Any {
        val response = api.getStatus()
        return response
    }
    //-------------------------Types-of-proyects----------------------------------------
    suspend fun getType(): Any {
        val response = api.getType()
        return response
    }
    //-----------------------user-login--------------------
    suspend fun loginUser(userLogin: UserLogin): Any {
        val response = api.loginUser(userLogin)
        return response
    }
    suspend fun authCreate(userAuth: UserAuth): Any {
        val response = api.authCreate(userAuth)
        return response
    }
    suspend fun authCheck(token: String,userCheckAuth: UserCheckAuth): Any {
        val response = api.authCheck(token,userCheckAuth)
        return response
    }
    suspend fun checkData(token:String):Any{
        val response = api.checkData(token)
        return response
    }
    suspend fun registerUser(userRegister: UserRegister): Any {
        val response = api.registerUser(userRegister)
        return response
    }
    suspend fun uploadUserData(token:String, image: MultipartBody.Part, userName: RequestBody, about: RequestBody,occupation: RequestBody):Any{
        val response = api.uploadUserData(token,image,userName,about,occupation)
        return response
    }
    suspend fun uploadUserData1(token:String, userName: RequestBody,about: RequestBody,occupation: RequestBody):Any{
        val response = api.uploadUserData1(token,userName,about,occupation)
        return response
    }
    suspend fun getUserDataIn(token:String):Any{
        val response = api.getUserData(token)
        return response
    }
    suspend fun postProyect(token:String,postProyect: PostProyect):Any{
        val response = api.postProyect(token,postProyect)
        return response
    }
    suspend fun putProyect(id:String,updateProyect: UpdateProyect):Any{
        val response = api.putProyect(id,updateProyect)
        return response
    }
    //-------------------------------local-data----------------------------------------
    suspend fun insertUserData(userToken: UserToken){
        userDao.insertUserData(userToken.toDatabase())
    }
    suspend fun getUserData():UserEntity{
        val response = userDao.getUserData()
        return response
    }
    suspend fun deleteDataUser(){
        userDao.deleteDataUser()
    }
    //socket
}