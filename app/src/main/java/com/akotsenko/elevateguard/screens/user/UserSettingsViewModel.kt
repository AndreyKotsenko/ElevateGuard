package com.akotsenko.elevateguard.screens.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.auth.AuthRepository
import com.akotsenko.elevateguard.model.user.UserRepository
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.utils.MutableUnitLiveEvent
import com.akotsenko.elevateguard.utils.publishEvent
import kotlinx.coroutines.launch

class UserSettingsViewModel(
    private val authRepository: AuthRepository = Singletons.authRepository,
    private val userRepository: UserRepository = Singletons.userRepository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user = _user

    private val _navigateToSignInEvent = MutableUnitLiveEvent()
    val navigateToSignInEvent = _navigateToSignInEvent

    fun getUser() {
        viewModelScope.launch {
            _user.value = userRepository.getCurrentUser()
        }
    }

    fun updateUser(user: User, password: String?) {
        viewModelScope.launch {
            userRepository.updateCurrentUser(user, password)
            _user.value = userRepository.getCurrentUser()
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            launchSignInScreen()
        }
    }

    private fun launchSignInScreen() = _navigateToSignInEvent.publishEvent()
}