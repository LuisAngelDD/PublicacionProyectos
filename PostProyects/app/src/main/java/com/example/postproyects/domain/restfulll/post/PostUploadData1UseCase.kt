package com.example.postproyects.domain.restfulll.post

import com.example.postproyects.data.Repository
import okhttp3.RequestBody
import javax.inject.Inject

class PostUploadData1UseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(token:String, userName: RequestBody,about: RequestBody,occupation: RequestBody) = repository.uploadUserData1(token,userName,about,occupation)
}