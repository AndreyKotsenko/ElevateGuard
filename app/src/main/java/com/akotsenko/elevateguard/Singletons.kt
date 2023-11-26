package com.akotsenko.elevateguard

import android.content.Context
import com.akotsenko.elevateguard.model.accident.AccidentRepository
import com.akotsenko.elevateguard.model.accident.AccidentSource
import com.akotsenko.elevateguard.model.auth.AuthRepository
import com.akotsenko.elevateguard.model.auth.AuthSource
import com.akotsenko.elevateguard.model.construction.ConstructionRepository
import com.akotsenko.elevateguard.model.construction.ConstructionSource
import com.akotsenko.elevateguard.model.facility.FacilityRepository
import com.akotsenko.elevateguard.model.facility.FacilitySource
import com.akotsenko.elevateguard.model.settings.AppSettings
import com.akotsenko.elevateguard.model.settings.AppSettingsRepository
import com.akotsenko.elevateguard.model.settings.SharedPreferencesAppSettings
import com.akotsenko.elevateguard.model.user.UserRepository
import com.akotsenko.elevateguard.model.user.UserSource
import com.akotsenko.elevateguard.sources.accident.RetrofitAccidentSource
import com.akotsenko.elevateguard.sources.auth.RetrofitAuthSource
import com.akotsenko.elevateguard.sources.construction.RetrofitConstructionSource
import com.akotsenko.elevateguard.sources.facility.RetrofitFacilitySource
import com.akotsenko.elevateguard.sources.user.RetrofitUserSource

object Singletons {

    private lateinit var appContext: Context

    private val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(appContext)
    }

    // sources

    private val authSource: AuthSource by lazy {
        RetrofitAuthSource()
    }

    private val constructionSource: ConstructionSource by lazy {
        RetrofitConstructionSource()
    }

    private val facilitySource: FacilitySource by lazy {
        RetrofitFacilitySource()
    }

    private val userSource: UserSource by lazy {
        RetrofitUserSource()
    }

    private val accidentSource: AccidentSource by lazy {
        RetrofitAccidentSource()
    }

    // repositories

    val authRepository: AuthRepository by lazy {
        AuthRepository(
            authSource = authSource,
            appSettings = appSettings
        )
    }

    val constructionRepository: ConstructionRepository by lazy {
        ConstructionRepository(
            constructionSource = constructionSource,
            appSettings = appSettings
        )
    }

    val facilityRepository: FacilityRepository by lazy {
        FacilityRepository(
            facilitySource = facilitySource,
            appSettings = appSettings
        )
    }

    val userRepository: UserRepository by lazy {
        UserRepository(
            userSource = userSource,
            appSettings = appSettings
        )
    }

    val appSettingsRepository: AppSettingsRepository by lazy {
        AppSettingsRepository(
            appSettings = appSettings
        )
    }

    val accidentRepository: AccidentRepository by lazy {
        AccidentRepository(
            accidentSource = accidentSource,
            appSettings = appSettings
        )
    }

    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }

}