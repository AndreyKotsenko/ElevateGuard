package com.akotsenko.elevateguard.screens.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navOptions
import com.akotsenko.elevateguard.Const
import com.akotsenko.elevateguard.R
import com.akotsenko.elevateguard.databinding.FragmentManagerSettingsBinding
import com.akotsenko.elevateguard.model.settings.entities.SettingsUserData
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.utils.findTopNavController
import com.akotsenko.elevateguard.sources.user.entities.GetUserResponseEntity
import com.akotsenko.elevateguard.utils.observeEvent

class ManagerSettingsFragment: Fragment(R.layout.fragment_manager_settings) {

    private lateinit var binding: FragmentManagerSettingsBinding
    private lateinit var viewModel: ManagerSettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerSettingsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(ManagerSettingsViewModel::class.java)

        val userRole = viewModel.getUserRole()

        if (userRole == ADMIN_ROLE) {
            binding.companySaveButton.visibility = View.VISIBLE
            binding.companyNameEditText.isFocusable = true
        } else {
            binding.companySaveButton.visibility = View.GONE
            binding.companyNameEditText.isFocusable = false
        }

        binding.companySaveButton.setOnClickListener {
            viewModel.updateFacility(binding.companyNameEditText.text.toString())
        }

        binding.saveButton.setOnClickListener {
            viewModel.updateUser(getPersonalInfo(), null)
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }

        binding.changeFacilityButton.setOnClickListener {
            navigateToSelectFacility()
        }

        observeToSignInScreen()
        observeState()
        observeFacility()
        observeUser()
        observeShowFacilityNotFoundToastEvent()

        viewModel.getCurrentUser()
        viewModel.getCurrentFacility()

        return binding.root
    }

    private fun observeToSignInScreen() = viewModel.navigateToSignInEvent.observeEvent(viewLifecycleOwner) {
        findTopNavController().navigate(R.id.signInFragment, null, navOptions {
            popUpTo(R.id.signInFragment) {
                inclusive = true
            }
        })
    }

    private fun observeUser() = viewModel.user.observe(viewLifecycleOwner) {
        setUserInfo(it)
    }

    private fun navigateToSelectFacility() {
        findTopNavController().navigate(R.id.selectFacilityFragment, null, navOptions {
            popUpTo(R.id.selectFacilityFragment) {
                inclusive = true
            }
        })
    }

    private fun observeShowFacilityNotFoundToastEvent() = viewModel.showFacilityNotFoundToastEvent.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        navigateToSelectFacility()
    }

    private fun observeFacility() = viewModel.facilityName.observe(viewLifecycleOwner) {
        setFacilityInfo(it)
    }

    private fun setFacilityInfo(facilityName: String) {
        binding.companyNameEditText.setText(facilityName)
    }

    private fun setUserInfo(user: SettingsUserData){
        with(binding){
            firstNameEditText.setText(user.firstName)
            lastNameEditText.setText(user.lastName)
            emailEditText.setText(user.email)
            mobileEditText.setText(user.mobile)
            pushNotificationCheckBox.isChecked = user.isReceivePushNotification == 1
            smsNotificationCheckBox.isChecked = user.isReceiveSmsNotification == 1
            emailNotificationCheckBox.isChecked = user.isReceiveEmailNotification == 1
        }
    }

    private fun getPersonalInfo(): User {
        return with(binding) {
            User(
                firstName = firstNameEditText.text.toString(),
                lastName = lastNameEditText.text.toString(),
                email = emailEditText.text.toString(),
                mobile = mobileEditText.text.toString(),
                isReceivePushNotification = if(pushNotificationCheckBox.isChecked) 1 else 0,
                isReceiveSmsNotification = if(smsNotificationCheckBox.isChecked) 1 else 0,
                isReceiveEmailNotification = if(emailNotificationCheckBox.isChecked) 1 else 0
            )
        }
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        private const val ADMIN_ROLE = "ADMIN"
    }
}