package com.example.postproyects.data.network

import com.example.postproyects.data.model.ApiError
import com.example.postproyects.data.model.proyects.PostProyect
import com.example.postproyects.data.model.proyects.UpdateProyect
import com.example.postproyects.data.model.users.UserAuth
import com.example.postproyects.data.model.users.UserCheckAuth
import com.example.postproyects.data.model.users.UserLogin
import com.example.postproyects.data.model.users.UserRegister
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class Service @Inject constructor(private val api: ApiClient) {
    //---------------------proyects-----------------------------------------------------
    suspend fun getSearchProyect(status: String,type: String,name:String): Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getSearchPr(status,type,name)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            }catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun getSearchMyPr(token:String,status: String,type: String,name:String): Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getSearchMyPr(token,status,type,name)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            }catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun getSearchMyPrFll(token:String,status: String,type: String,name:String): Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getSearchMyPrFll(token,status,type,name)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            }catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun getSearchFll(codes:String): Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getSearchFll(codes)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun getSearchLK(codes:String): Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getSearchLK(codes)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun getProyect(code:String):Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getProyect(code)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception) {
                "Fallo de conexion"
            }
        }
    }
    suspend fun getFollowsProyect(code:String):Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getFollowsProyect(code)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun getLikesProyect(code:String):Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getLikesProyect(code)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun getIfFollowsProyect(token:String,code:String):Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getIfFollowsProyect(token,code)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun getIfLikesProyect(token:String,code:String):Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getIfLikesProyect(token,code)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun setFollowsProyect(token:String,code:String):Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.setFollowProyect(token,code)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun setLikesProyect(token:String,code:String):Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.setLikeProyect(token,code)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    //-----------------------Status-----------------------------------------------------
    suspend fun getStatus(): Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getStatus()
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun getType(): Any{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getType()
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun registerUser(userRegister: UserRegister): Any {
        return withContext(Dispatchers.IO){
            try {
                val response = api.registerUser(userRegister)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun loginUser(userLogin: UserLogin): Any {
        return withContext(Dispatchers.IO){
            try {
                val response = api.loginUser(userLogin)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun authCreate(userAuth: UserAuth): Any {
        return withContext(Dispatchers.IO){
            try {
                val response = api.authCreate(userAuth)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun authCheck(token: String,userCheckAuth: UserCheckAuth): Any {
        return withContext(Dispatchers.IO){
            try {
                val response = api.authCheck(token,userCheckAuth)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun checkData(token:String):Any{
        return withContext(Dispatchers.IO){
            try {
                val response = api.checkData(token)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun uploadUserData(token:String,image: MultipartBody.Part,userName: RequestBody,about: RequestBody,occupation: RequestBody):Any{
        return withContext(Dispatchers.IO){
            try {
                val response = api.uploadUserData(token,image,userName,about,occupation)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun uploadUserData1(token:String,userName: RequestBody,about: RequestBody,occupation: RequestBody):Any{
        return withContext(Dispatchers.IO){
            try {
                val response = api.uploadUserData1(token,userName,about,occupation)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun getUserData(token:String):Any{
        return withContext(Dispatchers.IO){
            try {
                val response = api.getUserData(token)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun postProyect(token:String,postProyect: PostProyect):Any{
        return withContext(Dispatchers.IO){
            try {
                val response = api.postProyect(token,postProyect)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
    suspend fun putProyect(id:String,updateProyect: UpdateProyect):Any{
        return withContext(Dispatchers.IO){
            try {
                val response = api.putProyect(id,updateProyect)
                response.body() ?: ApiError(response.errorBody()!!.source().buffer.snapshot().utf8(),response.code())
            } catch (ex:Exception){
                "Fallo de conexion"
            }
        }
    }
}