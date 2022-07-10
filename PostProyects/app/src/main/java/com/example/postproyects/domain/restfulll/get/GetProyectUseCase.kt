package com.example.postproyects.domain.restfulll.get

import com.example.postproyects.data.Repository
import javax.inject.Inject

class GetProyectUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(code:String) = repository.getProyect(code)
}