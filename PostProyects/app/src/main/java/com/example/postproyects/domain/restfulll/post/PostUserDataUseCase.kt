package com.example.postproyects.domain.restfulll.post

import com.example.postproyects.data.Repository
import javax.inject.Inject

class PostUserDataUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(token:String) = repository.getUserDataIn(token)
}