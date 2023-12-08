package com.akotsenko.elevateguard.screens.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.EmptyFieldException
import com.akotsenko.elevateguard.model.Field
import com.akotsenko.elevateguard.model.SignInValidateException
import com.akotsenko.elevateguard.model.auth.AuthRepository
import com.akotsenko.elevateguard.model.auth.entities.Credential
import com.akotsenko.elevateguard.screens.base.BaseViewModel
import com.akotsenko.elevateguard.utils.*
import kotlinx.coroutines.launch


class SignInViewModel(private val authRepository: AuthRepository = Singletons.authRepository) :
    BaseViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state

    private val _clearAllFieldsEvent = MutableUnitLiveEvent()
    val clearAllFieldsEvent = _clearAllFieldsEvent

    private val _showAuthErrorToastEvent = MutableLiveEvent<String>()
    val showAuthToastEvent = _showAuthErrorToastEvent.share()

    private val _navigateToUserTabsEvent = MutableUnitLiveEvent()
    val navigateToUserTabsEvent = _navigateToUserTabsEvent

    private val _navigateToManagerTabsEvent = MutableUnitLiveEvent()
    val navigateToManagerTabsEvent = _navigateToManagerTabsEvent

    fun login(loginData: Credential) {
        viewModelScope.launch {
            showProgress()
            try {
                val account = authRepository.login(loginData)

                if (account.role == USER_ROLE) {
                    launchUserTabsScreen()
                } else {
                    launchManagerTabsScreen()
                }
            } catch (e: SignInValidateException) {
                processSignInValidateException(e)
            } catch (e: EmptyFieldException){
                processEmptyFieldException(e)
            } finally {
                hideProgress()
            }
        }
    }

    private fun processSignInValidateException(e: SignInValidateException) {
        clearAllFields()
        showAuthErrorToast(e.message.toString())
    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = _state.requireValue().copy(
            emptyEmailError = e.field == Field.Email,
            emptyPasswordError = e.field == Field.Password,
        )
    }

    private fun clearAllFields() = _clearAllFieldsEvent.publishEvent()

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.value?.copy(signInInProgress = false)
    }

    private fun showAuthErrorToast(message: String) = _showAuthErrorToastEvent.publishEvent(message)

    private fun launchUserTabsScreen() = _navigateToUserTabsEvent.publishEvent()

    private fun launchManagerTabsScreen() = _navigateToManagerTabsEvent.publishEvent()

    data class State(
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
    }

    companion object {
        private const val USER_ROLE = "USER"
        private const val MANAGER_ROLE = "MANAGER"
    }
}