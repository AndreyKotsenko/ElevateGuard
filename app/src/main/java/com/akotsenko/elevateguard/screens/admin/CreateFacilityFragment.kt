package com.akotsenko.elevateguard.screens.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akotsenko.elevateguard.R
import com.akotsenko.elevateguard.databinding.FragmentCreateFacilityManagerBinding
import com.akotsenko.elevateguard.model.auth.entities.RegisterData
import com.akotsenko.elevateguard.utils.observeEvent
import com.akotsenko.elevateguard.utils.observeToSignInScreen

class CreateFacilityFragment: Fragment(R.layout.fragment_create_facility_manager) {

    private lateinit var binding: FragmentCreateFacilityManagerBinding
    private lateinit var viewModel: CreateFacilityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateFacilityManagerBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(CreateFacilityViewModel::class.java)

        observeToSignInScreen(viewModel.navigateToSignInEvent)
        observeState()
        observeShowFacilityAlreadyExistEvent()
        observeNavigateToAdminTabsEvent()

        binding.createButton.setOnClickListener {
            viewModel.createFacilityAndManager(binding.companyNameEditText.toString(), toRegisterData())
        }

        return binding.root
    }

    private fun observeShowFacilityAlreadyExistEvent() = viewModel.showFacilityAlreadyExistEvent.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.companyNameEditText.error = if (it.emptyFacilityNameError) getString(R.string.field_is_empty) else null
        binding.firstNameEditText.error = if (it.emptyFirstNameError) getString(R.string.field_is_empty) else null
        binding.lastNameEditText.error = if (it.emptyLastNameError) getString(R.string.field_is_empty) else null
        binding.mobileEditText.error = if (it.emptyMobileError) getString(R.string.field_is_empty) else null
        binding.emailEditText.error = if (it.emptyEmailError) getString(R.string.field_is_empty) else null
        binding.passwordEditText.error = if (it.emptyPasswordError) getString(R.string.field_is_empty) else null

        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeNavigateToAdminTabsEvent() = viewModel.navigateToAdminTabsEvent.observeEvent(viewLifecycleOwner) {
        findNavController().navigate(R.id.action_createFacilityFragment_to_managerTabsFragment)
    }

    private fun toRegisterData(): RegisterData {
        val registerData = with(binding) {
            RegisterData(
                userFirstName = firstNameEditText.toString(),
                userLastName = lastNameEditText.toString(),
                userEmail = emailEditText.toString(),
                userMobile = mobileEditText.toString(),
                userPassword = passwordEditText.toString(),
                userRole = "MANAGER",
                userFacilityId = 0,
                userIsReceiveEmailNotification = if(emailNotificationCheckBox.isChecked) 1 else 0,
                userIsReceiveSmsNotification = if(smsNotificationCheckBox.isChecked) 1 else 0,
                userIsReceivePushNotification = if(pushNotificationCheckBox.isChecked) 1 else 0
            )
        }

        return registerData
    }
}