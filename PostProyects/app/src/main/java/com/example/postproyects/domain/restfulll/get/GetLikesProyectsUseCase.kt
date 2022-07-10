package com.example.postproyects.domain.restfulll.get

import com.example.postproyects.data.Repository
import javax.inject.Inject

class GetLikesProyectsUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(code:String) = repository.getLikesProyect(code)
}