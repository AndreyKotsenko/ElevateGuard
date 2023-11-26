package com.akotsenko.elevateguard.screens.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navOptions
import com.akotsenko.elevateguard.R
import com.akotsenko.elevateguard.databinding.FragmentUserSettingsBinding
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.utils.findTopNavController
import com.akotsenko.elevateguard.utils.observeEvent

class UserSettingsFragment: Fragment(R.layout.fragment_user_settings) {

    private lateinit var binding: FragmentUserSettingsBinding
    private lateinit var viewModel: UserSettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserSettingsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(UserSettingsViewModel::class.java)

        observeToSignInScreen()
        observeUser()
        viewModel.getUser()

        binding.saveButton.setOnClickListener {
            val user = toUser()

            viewModel.updateUser(user, null)
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }

        return binding.root
    }

    private fun toUser(): User {
        return User(
            firstName = binding.firstNameEditText.text.toString(),
            lastName = binding.lastNameEditText.text.toString(),
            email = binding.emailEditText.text.toString(),
            mobile = binding.mobileEditText.text.toString(),
            isReceivePushNotification = if (binding.pushNotificationCheckBox.isChecked) 1 else 0,
            isReceiveSmsNotification = if (binding.smsNotificationCheckBox.isChecked) 1 else 0,
            isReceiveEmailNotification = if (binding.emailNotificationCheckBox.isChecked) 1 else 0
        )
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

    private fun observeUser() = viewModel.user.observe(viewLifecycleOwner) {
        setUserInfo(it)
    }

    private fun observeToSignInScreen() = viewModel.navigateToSignInEvent.observeEvent(viewLifecycleOwner) {
        findTopNavController().navigate(R.id.signInFragment, null, navOptions {
            popUpTo(R.id.signInFragment) {
                inclusive = true
            }
        })
    }


}