package com.akotsenko.elevateguard.screens.manager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.AuthException
import com.akotsenko.elevateguard.model.ConstructionNotFoundException
import com.akotsenko.elevateguard.model.FacilityNotFoundException
import com.akotsenko.elevateguard.model.accident.AccidentRepository
import com.akotsenko.elevateguard.model.accident.entities.Accident
import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.model.facility.FacilityRepository
import com.akotsenko.elevateguard.screens.auth.SignInViewModel
import com.akotsenko.elevateguard.screens.base.BaseViewModel
import com.akotsenko.elevateguard.utils.MutableLiveEvent
import com.akotsenko.elevateguard.utils.publishEvent
import com.akotsenko.elevateguard.utils.share
import kotlinx.coroutines.launch

class ManagerAccidentViewModel(
    private val accidentRepository: AccidentRepository = Singletons.accidentRepository,
    private val facilityRepository: FacilityRepository = Singletons.facilityRepository
) :
    BaseViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state

    private val _accidents = MutableLiveData<List<Accident>>()
    val accidents = _accidents

    private val _constructions = MutableLiveData<List<Construction>>()
    val constructions = _constructions

    private val _showFacilityNotFoundToastEvent = MutableLiveEvent<String>()
    val showFacilityNotFoundToastEvent = _showFacilityNotFoundToastEvent.share()

    private val _showConstructionNotFoundToastEvent = MutableLiveEvent<String>()
    val showConstructionNotFoundToastEvent = _showConstructionNotFoundToastEvent.share()

    fun getAccidents() {
        viewModelScope.launch {
            showProgress()
            try {
                _accidents.value = accidentRepository.getAccidentsByFacility()
            } catch (e: AuthException) {
                launchSignInScreen()
            } catch (e: FacilityNotFoundException) {
                processFacilityNotFoundException(e)
            } finally {
                hideProgress()
            }
        }
    }

    fun getConstructions() {
        viewModelScope.launch {
            try{
                _constructions.value = facilityRepository.getConstructionOfFacility()
            } catch (e: FacilityNotFoundException) {
                processFacilityNotFoundException(e)
            } catch (e: AuthException) {
                launchSignInScreen()
            }

        }
    }

    fun createAccident(constructionId: Int) {
        viewModelScope.launch {
            showProgress()
            try {
                accidentRepository.createAccident(constructionId)
                _accidents.value = accidentRepository.getAccidentsByFacility()
            } catch (e: AuthException) {
                launchSignInScreen()
            } catch (e: ConstructionNotFoundException) {
                processConstructionNotFoundException(e)
            } catch (e: FacilityNotFoundException) {
                processFacilityNotFoundException(e)
            } finally {
                hideProgress()
            }
        }
    }

    private fun processConstructionNotFoundException(e: ConstructionNotFoundException) {
        _showConstructionNotFoundToastEvent.publishEvent(e.message.toString())
    }

    private fun processFacilityNotFoundException(e: FacilityNotFoundException) {
        _showFacilityNotFoundToastEvent.publishEvent(e.message.toString())
    }

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.value?.copy(signInInProgress = false)
    }

    data class State(
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
    }

}