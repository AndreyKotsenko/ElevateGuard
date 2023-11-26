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

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }

        observeToSignInScreen()
        observeUser()

        viewModel.getCurrentUser()

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

    private fun setUserInfo(user: User){
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
}