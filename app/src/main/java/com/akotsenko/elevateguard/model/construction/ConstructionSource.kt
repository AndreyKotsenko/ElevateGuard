package com.akotsenko.elevateguard.model.construction

import com.akotsenko.elevateguard.model.construction.entities.Construction

interface ConstructionSource {

    suspend fun createConstruction(authToken: String, construction: Construction): Construction

    suspend fun updateConstruction(authToken: String, constructionId: String, construction: Construction): String

    suspend fun deleteConstruction(authToken: String, constructionId: String)

}