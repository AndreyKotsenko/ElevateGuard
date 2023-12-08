package com.akotsenko.elevateguard.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.akotsenko.elevateguard.R

fun Fragment.findTopNavController(): NavController {
    val topLevelHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}

fun Fragment.observeToSignInScreen(navigateToSignInEvent: MutableUnitLiveEvent) = navigateToSignInEvent.observeEvent(viewLifecycleOwner) {
    findTopNavController().navigate(R.id.signInFragment, null, navOptions {
        popUpTo(R.id.signInFragment) {
            inclusive = true
        }
    })
}