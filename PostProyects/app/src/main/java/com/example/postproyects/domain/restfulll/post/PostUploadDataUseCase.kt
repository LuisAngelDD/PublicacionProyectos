package com.example.postproyects.domain.restfulll.post

import com.example.postproyects.data.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PostUploadDataUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(token:String,image: MultipartBody.Part,userName: RequestBody,about: RequestBody,occupation: RequestBody) = repository.uploadUserData(token,image,userName,about,occupation)
}