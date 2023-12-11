package com.akotsenko.elevateguard.screens.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akotsenko.elevateguard.R
import com.akotsenko.elevateguard.databinding.FragmentUserUsersBinding
import com.akotsenko.elevateguard.screens.adapters.UserUsersAdapter
import com.akotsenko.elevateguard.utils.observeToSignInScreen

class UserUsersFragment: Fragment(R.layout.fragment_user_users) {

    private lateinit var binding: FragmentUserUsersBinding
    private lateinit var viewModel: UserUsersViewModel
    private lateinit var adapter: UserUsersAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserUsersBinding.inflate(inflater, container, false)

        adapter = UserUsersAdapter(emptyList())

        viewModel = ViewModelProvider(this).get(UserUsersViewModel::class.java)

        observeToSignInScreen(viewModel.navigateToSignInEvent)
        observeState()
        observeUsers()

        viewModel.getUsers()

        binding.usersList.adapter = adapter

        return binding.root
    }

    private fun observeUsers() = viewModel.users.observe(viewLifecycleOwner) {
        adapter.setList(it)
    }


    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }
}