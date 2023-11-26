package com.akotsenko.elevateguard.model.facility.entities

import com.akotsenko.elevateguard.model.construction.entities.Construction

data class Facility(
    val name: String,
    val constructionOfFacility: List<Construction>
)
