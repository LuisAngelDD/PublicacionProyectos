package com.example.postproyects.domain.localdata

import com.example.postproyects.data.Repository
import javax.inject.Inject

class clearData @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = repository.deleteDataUser()
}