package com.vti.messageapp.domain.usecases

interface UseCase<T, P> {
    suspend fun invoke(param: P): T
}

class NoParam {}