package com.example.postproyects.domain.restfulll.put

import com.example.postproyects.data.Repository
import com.example.postproyects.data.model.proyects.UpdateProyect
import javax.inject.Inject

class PutProyectUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id:String,updateProyect: UpdateProyect) = repository.putProyect(id,updateProyect)}