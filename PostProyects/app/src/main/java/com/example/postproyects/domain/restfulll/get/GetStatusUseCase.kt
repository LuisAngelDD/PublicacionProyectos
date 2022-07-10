package com.example.postproyects.domain.restfulll.get

import com.example.postproyects.data.Repository
import javax.inject.Inject

class GetStatusUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = repository.getStatus()
}