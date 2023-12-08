package com.akotsenko.elevateguard.screens.base

import androidx.lifecycle.ViewModel
import com.akotsenko.elevateguard.utils.MutableUnitLiveEvent
import com.akotsenko.elevateguard.utils.publishEvent

open class BaseViewModel: ViewModel() {

    protected val _navigateToSignInEvent = MutableUnitLiveEvent()
    val navigateToSignInEvent = _navigateToSignInEvent

    protected fun launchSignInScreen() = _navigateToSignInEvent.publishEvent()
}