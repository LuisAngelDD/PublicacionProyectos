package com.example.postproyects.domain.restfulll.post

import com.example.postproyects.data.Repository
import com.example.postproyects.data.model.proyects.PostProyect
import javax.inject.Inject

class PostProyectUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(token:String,postProyect: PostProyect) = repository.postProyect(token,postProyect)
}