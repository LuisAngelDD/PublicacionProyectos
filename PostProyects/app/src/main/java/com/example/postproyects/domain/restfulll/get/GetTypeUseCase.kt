package com.example.postproyects.domain.restfulll.get

import com.example.postproyects.data.Repository
import javax.inject.Inject

class GetTypeUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = repository.getType()
}