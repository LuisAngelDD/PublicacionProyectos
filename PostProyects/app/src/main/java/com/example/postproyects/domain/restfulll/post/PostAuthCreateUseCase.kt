package com.example.postproyects.domain.restfulll.post

import com.example.postproyects.data.Repository
import com.example.postproyects.data.model.users.UserAuth
import javax.inject.Inject

class PostAuthCreateUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(userAuth: UserAuth) = repository.authCreate(userAuth)
}