package com.example.postproyects.domain.restfulll.get

import com.example.postproyects.data.Repository
import javax.inject.Inject

class SetLikeProyectUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(token:String,code:String) = repository.setLikeProyect(token,code)
}