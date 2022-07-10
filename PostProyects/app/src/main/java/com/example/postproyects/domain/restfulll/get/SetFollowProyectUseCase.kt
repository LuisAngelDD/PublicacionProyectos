package com.example.postproyects.domain.restfulll.get

import com.example.postproyects.data.Repository
import javax.inject.Inject

class SetFollowProyectUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(token:String,code:String) = repository.setFollowProyect(token,code)
}