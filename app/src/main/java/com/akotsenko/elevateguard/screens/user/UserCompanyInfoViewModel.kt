package com.akotsenko.elevateguard.screens.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.AuthException
import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.model.facility.FacilityRepository
import com.akotsenko.elevateguard.screens.auth.SignInViewModel
import com.akotsenko.elevateguard.screens.base.BaseViewModel
import com.akotsenko.elevateguard.utils.MutableUnitLiveEvent
import kotlinx.coroutines.launch

class UserCompanyInfoViewModel(private val facilityRepository: FacilityRepository = Singletons.facilityRepository) : BaseViewModel() {

    private val _constructions = MutableLiveData<List<Construction>>()
    val constructions = _constructions

    private val _state = MutableLiveData(State())
    val state = _state

    fun getConstructionsOfFacility() {
        viewModelScope.launch {
            try{
                showProgress()
                _constructions.value = facilityRepository.getConstructionOfFacility()
                hideProgress()
            } catch (e: AuthException) {
                launchSignInScreen()
            }
        }
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