package com.akotsenko.elevateguard.screens.manager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.AuthException
import com.akotsenko.elevateguard.model.FacilityNotFoundException
import com.akotsenko.elevateguard.model.FailedDeleteUserException
import com.akotsenko.elevateguard.model.UserValidateException
import com.akotsenko.elevateguard.model.auth.AuthRepository
import com.akotsenko.elevateguard.model.auth.entities.RegisterData
import com.akotsenko.elevateguard.model.facility.FacilityRepository
import com.akotsenko.elevateguard.model.user.UserRepository
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.screens.base.BaseViewModel
import com.akotsenko.elevateguard.utils.MutableLiveEvent
import com.akotsenko.elevateguard.utils.publishEvent
import com.akotsenko.elevateguard.utils.share
import kotlinx.coroutines.launch

class ManagerUsersViewModel(
    private val userRepository: UserRepository = Singletons.userRepository,
    private val facilityRepository: FacilityRepository = Singletons.facilityRepository,
    private val authRepository: AuthRepository = Singletons.authRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users = _users

    private val _state = MutableLiveData(State())
    val state = _state

    private val _showFacilityNotFoundToastEvent = MutableLiveEvent<String>()
    val showFacilityNotFoundToastEvent = _showFacilityNotFoundToastEvent.share()

    private val _showUserValidateToastEvent = MutableLiveEvent<String>()
    val showUserValidateToastEvent = _showUserValidateToastEvent.share()

    private val _showFailedDeleteUserToastEvent = MutableLiveEvent<String>()
    val showFailedDeleteUserToastEvent = _showFailedDeleteUserToastEvent.share()

    fun getUsers() {
        viewModelScope.launch {
            showProgress()
            try {
                _users.value = facilityRepository.getUsersByFacility()
            } catch (e: AuthException) {
                launchSignInScreen()
            } catch (e: FacilityNotFoundException) {
                processFacilityNotFoundException(e)
            } finally {
                hideProgress()
            }
        }
    }

    fun updateUser(
        userId: String,
        user: User,
        password: String
    ) {
        viewModelScope.launch {
            showProgress()
            try {
                userRepository.updateUser(userId, user, password)
                _users.value = facilityRepository.getUsersByFacility()
            } catch (e: AuthException) {
                launchSignInScreen()
            } catch (e: FacilityNotFoundException) {
                processFacilityNotFoundException(e)
            } catch (e: UserValidateException) {
                processUserValidateException(e)
            } catch (e: FailedDeleteUserException) {
                processFailedDeleteUserException(e)
            } finally {
                hideProgress()
            }

        }

    }

    fun createUser(registerData: RegisterData) {
        viewModelScope.launch {
            showProgress()
            try {
                authRepository.register(registerData)
                _users.value = facilityRepository.getUsersByFacility()
            } catch (e: AuthException) {
                launchSignInScreen()
            } catch (e: FacilityNotFoundException) {
                processFacilityNotFoundException(e)
            } finally {
                hideProgress()
            }
        }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            showProgress()
            try {
                userRepository.deleteUser(userId)
                _users.value = facilityRepository.getUsersByFacility()
            } catch (e: AuthException) {
                launchSignInScreen()
            } catch (e: FacilityNotFoundException) {
                processFacilityNotFoundException(e)
            } finally {
                hideProgress()
            }
        }
    }

    private fun processFacilityNotFoundException(e: FacilityNotFoundException) {
        _showFacilityNotFoundToastEvent.publishEvent(e.message.toString())
    }

    private fun processUserValidateException(e: UserValidateException) {
        _showUserValidateToastEvent.publishEvent(e.message.toString())
    }

    private fun processFailedDeleteUserException(e: FailedDeleteUserException) {
        _showFailedDeleteUserToastEvent.publishEvent(e.message.toString())
    }

    fun getCurrentFacilityId(): String {
        return facilityRepository.getCurrentFacilityId()
    }

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.value?.copy(signInInProgress = false)
    }

    data class State(
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
    }
}