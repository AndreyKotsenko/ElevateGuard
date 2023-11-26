package com.akotsenko.elevateguard.screens.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.akotsenko.elevateguard.R
import com.akotsenko.elevateguard.databinding.FragmentUserTabsBinding

class UserTabsFragment: Fragment(R.layout.fragment_user_tabs) {

    private lateinit var binding: FragmentUserTabsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserTabsBinding.bind(view)

        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }
}