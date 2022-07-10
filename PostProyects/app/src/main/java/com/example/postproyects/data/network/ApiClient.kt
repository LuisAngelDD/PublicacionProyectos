package com.example.postproyects.data.network

import com.example.postproyects.data.model.ApiResponse
import com.example.postproyects.data.model.proyects.*
import com.example.postproyects.data.model.users.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiClient {
    //Get
    //----------------------proyecto-----------------------------------------------------
    @GET("app/proyecto/get/{status}/{type}/{nombre}")
    suspend fun getSearchPr(@Path("status")status:String,@Path("type")type:String,@Path("nombre")nombre:String): Response<List<Pr>>
    @GET("app/proyecto/myProyects/{status}/{type}/{nombre}")
    suspend fun getSearchMyPr(@Header("Authorization")token:String,@Path("status")status:String,@Path("type")type:String,@Path("nombre")nombre:String): Response<List<Pr>>
    @GET("app/follow/myFollows/{status}/{type}/{nombre}")
    suspend fun getSearchMyPrFll(@Header("Authorization")token:String,@Path("status")status:String,@Path("type")type:String,@Path("nombre")nombre:String): Response<List<Pr>>
    @GET("app/follow/get/{code}")
    suspend fun getSearchFll(@Path("code")code:String): Response<List<Count>>
    @GET("app/like/get/{code}")
    suspend fun getSearchLK(@Path("code")code:String): Response<List<Count>>
    @GET("app/proyecto/code/{code}")
    suspend fun getProyect(@Path("code")code:String): Response<Pr>
    @GET("app/follow/count/{code}")
    suspend fun getFollowsProyect(@Path("code")code:String): Response<ApiResponse>
    @GET("app/like/count/{code}")
    suspend fun getLikesProyect(@Path("code")code:String): Response<ApiResponse>
    @GET("app/follow/if/{code}")
    suspend fun getIfFollowsProyect(@Header("Authorization")token:String,@Path("code")code:String): Response<ApiResponse>
    @GET("app/like/if/{code}")
    suspend fun getIfLikesProyect(@Header("Authorization")token:String,@Path("code")code:String): Response<ApiResponse>
    @GET("app/follow/addRemove/{code}")
    suspend fun setFollowProyect(@Header("Authorization")token:String,@Path("code")code:String): Response<ApiResponse>
    @GET("app/like/addRemove/{code}")
    suspend fun setLikeProyect(@Header("Authorization")token:String,@Path("code")code:String): Response<ApiResponse>
    //-----------------------------------Status------------------------------------------
    @GET("app/status/")
    suspend fun getStatus(): Response<List<StatusProyects>>
    //-------------------------------------Type------------------------------------------
    @GET("app/type/")
    suspend fun getType(): Response<List<TypeProyects>>
    //-------------------------------------User Data------------------------------------------
    @POST("app/usuario/info/")
    suspend fun getUserData(@Header("Authorization")token:String): Response<UserData>
    //-----------------------------------------------------------------------------------
    //Post
    //user
    @POST("app/usuario/signIn/")
    suspend fun loginUser(@Body userLogin: UserLogin): Response<UserToken>
    @POST("app/usuario/signUp/")
    suspend fun registerUser(@Body userRegister: UserRegister): Response<ApiResponse>
    @POST("app/usuario/authCreate/")
    suspend fun authCreate(@Body userAuth: UserAuth): Response<UserToken>
    @POST("app/usuario/authCheck/")
    suspend fun authCheck(@Header("Authorization")token:String,@Body userCheckAuth: UserCheckAuth): Response<UserToken>
    @POST("app/usuario/checkData/")
    suspend fun checkData(@Header("Authorization")token:String) : Response<ApiResponse>
    @Multipart
    @POST("app/usuario/uploadUserData/")
    suspend fun uploadUserData(@Header("Authorization")token:String, @Part image:MultipartBody.Part, @Part("userName") userName: RequestBody, @Part("about") about:RequestBody, @Part("occupation") occupation:RequestBody):Response<ApiResponse>
    @Multipart
    @POST("app/usuario/uploadUserData/")
    suspend fun uploadUserData1(@Header("Authorization")token:String, @Part("userName") userName: RequestBody, @Part("about") about:RequestBody, @Part("occupation") occupation:RequestBody):Response<ApiResponse>
    //proyect
    @POST("app/proyecto/post/")
    suspend fun postProyect(@Header("Authorization")token:String,@Body postProyect:PostProyect): Response<ApiResponse>
    @PUT("app/proyecto/update/{proyectoId}")
    suspend fun putProyect(@Path("proyectoId")id:String,@Body updateProyect: UpdateProyect): Response<ApiResponse>
}