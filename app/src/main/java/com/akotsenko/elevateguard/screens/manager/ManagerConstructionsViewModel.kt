package com.akotsenko.elevateguard.screens.manager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.AuthException
import com.akotsenko.elevateguard.model.EmptyFieldException
import com.akotsenko.elevateguard.model.FacilityNotFoundException
import com.akotsenko.elevateguard.model.Field
import com.akotsenko.elevateguard.model.construction.ConstructionRepository
import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.model.facility.FacilityRepository
import com.akotsenko.elevateguard.model.settings.AppSettingsRepository
import com.akotsenko.elevateguard.screens.base.BaseViewModel
import com.akotsenko.elevateguard.utils.MutableLiveEvent
import com.akotsenko.elevateguard.utils.publishEvent
import com.akotsenko.elevateguard.utils.requireValue
import com.akotsenko.elevateguard.utils.share
import kotlinx.coroutines.launch


class ManagerConstructionsViewModel(
    private val constructionRepository: ConstructionRepository = Singletons.constructionRepository,
    private val facilityRepository: FacilityRepository = Singletons.facilityRepository,
    private val appSettingsRepository: AppSettingsRepository = Singletons.appSettingsRepository
) : BaseViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state

    private val _constructions = MutableLiveData<List<Construction>>()
    val constructions = _constructions

    private val _showFacilityNotFoundToastEvent = MutableLiveEvent<String>()
    val showFacilityNotFoundToastEvent = _showFacilityNotFoundToastEvent.share()


    fun getConstructions() {
        viewModelScope.launch {
            showProgress()
            try {
                _constructions.value = facilityRepository.getConstructionOfFacility()
            } catch (e: AuthException) {
                launchSignInScreen()
            } catch (e: FacilityNotFoundException) {
                processFacilityNotFoundException(e)
            } finally {
                hideProgress()
            }

        }
    }

    fun getUserRole(): String = appSettingsRepository.getSettingsUserDataState().role

    fun updateConstruction(name: String, constructionId: String) {
        viewModelScope.launch {
            showProgress()
            try {
                constructionRepository.updateConstruction(constructionId, Construction(name = name))
                _constructions.value = facilityRepository.getConstructionOfFacility()
            } catch (e: AuthException) {
                launchSignInScreen()
            } catch (e: EmptyFieldException) {
                processEmptyFieldException(e)
            } catch (e: FacilityNotFoundException) {
                processFacilityNotFoundException(e)
            } finally {
                hideProgress()
            }
        }
    }

    fun createConstruction(name: String) {
        viewModelScope.launch {
            showProgress()
            try {
                constructionRepository.createConstruction(Construction(name = name))
                _constructions.value = facilityRepository.getConstructionOfFacility()
            } catch (e: AuthException) {
                launchSignInScreen()
            } catch (e: EmptyFieldException) {
                processEmptyFieldException(e)
            } catch (e: FacilityNotFoundException) {
                processFacilityNotFoundException(e)
            } finally {
                hideProgress()
            }
        }
    }

    fun deleteConstruction(constructionId: String) {
        viewModelScope.launch {
            showProgress()
            try {
                constructionRepository.deleteConstruction(constructionId)
                _constructions.value = facilityRepository.getConstructionOfFacility()
            } catch (e: AuthException) {
                launchSignInScreen()
            } catch (e: FacilityNotFoundException) {
                processFacilityNotFoundException(e)
            } finally {
                hideProgress()
            }
        }

    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = _state.requireValue().copy(
            emptyConstructionNameError = e.field == Field.ConstructionName
        )
    }

    private fun processFacilityNotFoundException(e: FacilityNotFoundException) {
        _showFacilityNotFoundToastEvent.publishEvent(e.message.toString())
    }


    private fun showProgress() {
        _state.value = _state.value?.copy(progress = true)
    }

    private fun hideProgress() {
        _state.value = _state.value?.copy(progress = false)
    }

    data class State(
        val emptyConstructionNameError: Boolean = false,
        val progress: Boolean = false
    ) {
        val showProgress: Boolean get() = progress
    }

}