package com.akotsenko.elevateguard.screens.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.model.facility.FacilityRepository
import kotlinx.coroutines.launch

class UserCompanyInfoViewModel(private val facilityRepository: FacilityRepository = Singletons.facilityRepository) : ViewModel() {

    private val _constructions = MutableLiveData<List<Construction>>()
    val constructions = _constructions

    fun getConstructionsOfFacility() {
        viewModelScope.launch {
            _constructions.value = facilityRepository.getConstructionOfFacility()
        }
    }
}