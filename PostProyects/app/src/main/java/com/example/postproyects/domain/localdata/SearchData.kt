package com.example.postproyects.domain.localdata

import com.example.postproyects.data.Repository
import javax.inject.Inject

class SearchData @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = repository.getUserData()
}