package com.akotsenko.elevateguard.screens.admin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.*
import com.akotsenko.elevateguard.model.auth.AuthRepository
import com.akotsenko.elevateguard.model.auth.entities.RegisterData
import com.akotsenko.elevateguard.model.facility.FacilityRepository
import com.akotsenko.elevateguard.model.facility.entities.Facility
import com.akotsenko.elevateguard.model.settings.AppSettingsRepository
import com.akotsenko.elevateguard.model.user.UserRepository
import com.akotsenko.elevateguard.screens.base.BaseViewModel
import com.akotsenko.elevateguard.utils.*
import kotlinx.coroutines.launch

class CreateFacilityViewModel(
    private val facilityRepository: FacilityRepository = Singletons.facilityRepository,
    private val authRepository: AuthRepository = Singletons.authRepository,
    private val appSettingsRepository: AppSettingsRepository = Singletons.appSettingsRepository
) : BaseViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state

    private val _showFacilityAlreadyExistToastEvent = MutableLiveEvent<String>()
    val showFacilityAlreadyExistEvent = _showFacilityAlreadyExistToastEvent.share()

    private val _showFacilityNotFoundToastEvent = MutableLiveEvent<String>()
    val showFacilityNotFoundToastEvent = _showFacilityNotFoundToastEvent.share()

    private val _navigateToAdminTabsEvent = MutableUnitLiveEvent()
    val navigateToAdminTabsEvent = _navigateToAdminTabsEvent

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.value?.copy(signInInProgress = false)
    }

    fun createFacilityAndManager(facilityName: String, manager: RegisterData) {
        viewModelScope.launch {
            showProgress()
            try {
                val facilityId = facilityRepository.createFacility(facilityName)

                val newManager = manager.copy(userFacilityId = facilityId)
                appSettingsRepository.saveCurrentFacilityId(facilityId)
                authRepository.register(newManager)
                launchAdminTabsScreen()
            } catch (e: AuthException) {
                launchSignInScreen()
            } catch (e: EmptyFieldException) {
                processEmptyFieldException(e)
            } catch (e: FacilityAlreadyExistException) {
                processFacilityAlreadyExistException(e)
            } catch (e: FacilityNotFoundException) {
                processFacilityNotFoundException(e)
            } finally {
                hideProgress()
            }


        }
    }

    private fun launchAdminTabsScreen() = _navigateToAdminTabsEvent.publishEvent()

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = _state.requireValue().copy(
            emptyFacilityNameError = e.field == Field.FacilityName,
            emptyFirstNameError = e.field == Field.FirstName,
            emptyLastNameError = e.field == Field.LastName,
            emptyMobileError = e.field == Field.Mobile,
            emptyEmailError = e.field == Field.Email,
            emptyPasswordError = e.field == Field.Password
        )
    }

    private fun processFacilityNotFoundException(e: FacilityNotFoundException) {
        _showFacilityNotFoundToastEvent.publishEvent(e.message.toString())
    }

    private fun processFacilityAlreadyExistException(e: FacilityAlreadyExistException){
        showFacilityAlreadyExistErrorToast(e.message.toString())
    }

    private fun showFacilityAlreadyExistErrorToast(message: String) = _showFacilityAlreadyExistToastEvent.publishEvent(message)

    data class State(
        val emptyFacilityNameError: Boolean = false,
        val emptyFirstNameError: Boolean = false,
        val emptyLastNameError: Boolean = false,
        val emptyMobileError: Boolean = false,
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
    }
}