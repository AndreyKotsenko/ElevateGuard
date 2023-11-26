package com.akotsenko.elevateguard.screens.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.akotsenko.elevateguard.R
import com.akotsenko.elevateguard.databinding.FragmentManagerTabsBinding
import com.akotsenko.elevateguard.databinding.FragmentUserTabsBinding

class ManagerTabsFragment: Fragment(R.layout.fragment_manager_tabs) {

    private lateinit var binding: FragmentManagerTabsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerTabsBinding.inflate(inflater, container, false)

        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        return binding.root
    }
}