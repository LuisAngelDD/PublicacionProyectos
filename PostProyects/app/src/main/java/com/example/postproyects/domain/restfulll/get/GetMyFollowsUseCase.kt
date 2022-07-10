package com.example.postproyects.domain.restfulll.get

import com.example.postproyects.data.Repository
import javax.inject.Inject

class GetMyFollowsUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(token:String,status: String,type: String,name:String) = repository.getSearchMyPrFll(token,status,type,name)
}