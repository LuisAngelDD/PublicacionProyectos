package com.example.postproyects.domain.localdata

import com.example.postproyects.data.Repository
import com.example.postproyects.data.model.users.UserToken
import javax.inject.Inject

class insertData @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(userToken: UserToken) = repository.insertUserData(userToken)
}