package com.example.postproyects.domain.restfulll.post

import com.example.postproyects.data.Repository
import com.example.postproyects.data.model.users.UserRegister
import javax.inject.Inject

class PostRegisterUserUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(userRegister: UserRegister) = repository.registerUser(userRegister)
}