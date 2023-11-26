package com.akotsenko.elevateguard.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akotsenko.elevateguard.R
import com.akotsenko.elevateguard.databinding.FragmentEnterCompanyBinding
import com.akotsenko.elevateguard.utils.observeEvent

class EnterCompanyFragment: Fragment(R.layout.fragment_enter_company) {

    private lateinit var binding: FragmentEnterCompanyBinding
    private lateinit var viewModel: EnterCompanyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnterCompanyBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(EnterCompanyViewModel::class.java)

        binding.enterCompanyButton.setOnClickListener {
            enterCompanyPressed(binding.companyEditText.text.toString())
        }

        observeState()
        observeNavigateToManagerTabsEvent()

        return binding.root
    }

    private fun enterCompanyPressed(name: String){
        viewModel.createFacility(name)
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeNavigateToManagerTabsEvent() = viewModel.navigateToManagerTabsEvent.observeEvent(viewLifecycleOwner) {
        findNavController().navigate(R.id.action_signInFragment_to_managerTabsFragment)
    }

}