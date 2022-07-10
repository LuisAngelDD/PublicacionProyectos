package com.example.postproyects.domain.restfulll.post

import com.example.postproyects.data.Repository
import com.example.postproyects.data.model.users.UserCheckAuth
import javax.inject.Inject

class PostAuthCheckUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(token: String,userCheckAuth: UserCheckAuth) = repository.authCheck(token,userCheckAuth)
}