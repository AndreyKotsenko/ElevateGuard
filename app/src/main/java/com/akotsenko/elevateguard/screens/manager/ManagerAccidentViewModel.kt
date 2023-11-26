package com.akotsenko.elevateguard.screens.manager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.accident.AccidentRepository
import com.akotsenko.elevateguard.model.accident.entities.Accident
import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.model.facility.FacilityRepository
import com.akotsenko.elevateguard.screens.auth.SignInViewModel
import kotlinx.coroutines.launch

class ManagerAccidentViewModel(
    private val accidentRepository: AccidentRepository = Singletons.accidentRepository,
    private val facilityRepository: FacilityRepository = Singletons.facilityRepository
) :
    ViewModel() {

    private val _state = MutableLiveData(SignInViewModel.State())
    val state = _state

    private val _accidents = MutableLiveData<List<Accident>>()
    val accidents = _accidents

    private val _constructions = MutableLiveData<List<Construction>>()
    val constructions = _constructions

    fun getAccidents() {
        viewModelScope.launch {
            showProgress()
            _accidents.value = accidentRepository.getAccidentsByFacility()
            hideProgress()
        }
    }

    fun getConstructions() {
        viewModelScope.launch {
            _constructions.value = facilityRepository.getConstructionOfFacility()
        }
    }

    fun createAccident(constructionId: Int) {
        viewModelScope.launch {
            showProgress()
            accidentRepository.createAccident(constructionId)
            _accidents.value = accidentRepository.getAccidentsByFacility()
            hideProgress()
        }
    }


    private fun showProgress() {
        _state.value = SignInViewModel.State(signInInProgress = true)
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