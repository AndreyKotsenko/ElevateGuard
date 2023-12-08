package com.akotsenko.elevateguard.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akotsenko.elevateguard.*
import com.akotsenko.elevateguard.databinding.FragmentSignInBinding
import com.akotsenko.elevateguard.model.auth.entities.Credential
import com.akotsenko.elevateguard.utils.observeEvent

class SignInFragment: Fragment(R.layout.fragment_sign_in) {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        binding.signInButton.setOnClickListener {
            onSignInButtonPressed(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
        }

        observeState()
        observeNavigateToUserTabsEvent()
        observeNavigateToManagerTabsEvent()
        observeShowAuthErrorMessageEvent()
        observeClearAllFieldsEvent()

        return binding.root
    }

    private fun onSignInButtonPressed(email: String, password: String) {
        viewModel.login(Credential(
            email = email,
            password = password
        ))
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.emailEditText.error = if (it.emptyEmailError) getString(R.string.field_is_empty) else null
        binding.passwordEditText.error = if (it.emptyPasswordError) getString(R.string.field_is_empty) else null

        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeShowAuthErrorMessageEvent() = viewModel.showAuthToastEvent.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
    }

    private fun observeClearAllFieldsEvent() = viewModel.clearAllFieldsEvent.observeEvent(viewLifecycleOwner) {
        binding.emailEditText.text?.clear()
        binding.passwordEditText.text?.clear()
    }

    private fun observeNavigateToUserTabsEvent() = viewModel.navigateToUserTabsEvent.observeEvent(viewLifecycleOwner) {
        findNavController().navigate(R.id.action_signInFragment_to_userTabsFragment)
    }

    private fun observeNavigateToManagerTabsEvent() = viewModel.navigateToManagerTabsEvent.observeEvent(viewLifecycleOwner) {
        findNavController().navigate(R.id.action_signInFragment_to_managerTabsFragment)
    }



}