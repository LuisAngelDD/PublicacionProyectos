package com.example.postproyects.domain.restfulll.get

import com.example.postproyects.data.Repository
import javax.inject.Inject

class GetSearchPrUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(status: String,type: String,name:String) = repository.searchProyects(status,type,name)
}