package com.akotsenko.elevateguard.model.auth

import com.akotsenko.elevateguard.model.auth.entities.Account
import com.akotsenko.elevateguard.model.auth.entities.Credential
import com.akotsenko.elevateguard.model.auth.entities.RegisterData
import com.akotsenko.elevateguard.sources.auth.entities.LoginResponseEntity

interface AuthSource {

    suspend fun login(loginData: Credential): Account

    suspend fun logout(authToken: String): String

    suspend fun register(authToken: String, registerData: RegisterData): Account
}