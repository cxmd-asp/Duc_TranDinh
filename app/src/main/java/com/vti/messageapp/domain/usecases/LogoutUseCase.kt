package com.vti.messageapp.domain.usecases

import com.vti.messageapp.domain.repositories.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authRepository: AuthRepository) :
    UseCase<Unit, NoParam> {
    override suspend fun invoke(param: NoParam) {
        authRepository.logout()
    }
}