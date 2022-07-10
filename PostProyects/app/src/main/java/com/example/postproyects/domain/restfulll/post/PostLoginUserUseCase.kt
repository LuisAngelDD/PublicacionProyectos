package com.example.postproyects.domain.restfulll.post

import com.example.postproyects.data.Repository
import com.example.postproyects.data.model.users.UserLogin
import javax.inject.Inject

class PostLoginUserUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(userLogin: UserLogin) = repository.loginUser(userLogin)
}