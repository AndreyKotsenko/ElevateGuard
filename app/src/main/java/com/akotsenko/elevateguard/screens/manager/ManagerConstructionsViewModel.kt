package com.akotsenko.elevateguard.screens.manager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.construction.ConstructionRepository
import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.model.facility.FacilityRepository
import com.akotsenko.elevateguard.screens.auth.SignInViewModel
import kotlinx.coroutines.launch


class ManagerConstructionsViewModel(
    private val constructionRepository: ConstructionRepository = Singletons.constructionRepository,
    private val facilityRepository: FacilityRepository = Singletons.facilityRepository
) : ViewModel() {

    private val _state = MutableLiveData(SignInViewModel.State())
    val state = _state

    private val _constructions = MutableLiveData<List<Construction>>()
    val constructions = _constructions


    fun getConstructions() {
        viewModelScope.launch {
            showProgress()
            _constructions.value = facilityRepository.getConstructionOfFacility()
            hideProgress()

        }
    }

    fun updateConstruction(name: String, constructionId: String) {
        viewModelScope.launch {
            showProgress()
            constructionRepository.updateConstruction(constructionId, Construction(name = name))
            _constructions.value = facilityRepository.getConstructionOfFacility()
            hideProgress()
        }
    }

    fun createConstruction(name: String) {
        viewModelScope.launch {
            showProgress()
            constructionRepository.createConstruction(Construction(name = name))
            _constructions.value = facilityRepository.getConstructionOfFacility()
            hideProgress()
        }
    }

    fun deleteConstruction(constructionId: String) {
        viewModelScope.launch {
            showProgress()
            constructionRepository.deleteConstruction(constructionId)
            _constructions.value = facilityRepository.getConstructionOfFacility()
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