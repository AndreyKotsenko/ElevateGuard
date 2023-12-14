package com.akotsenko.elevateguard.screens.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.akotsenko.elevateguard.*
import com.akotsenko.elevateguard.databinding.FragmentUserCompanyInfoBinding
import com.akotsenko.elevateguard.screens.adapters.UserConstructionsAdapter
import com.akotsenko.elevateguard.utils.findTopNavController
import com.akotsenko.elevateguard.utils.observeEvent
import com.akotsenko.elevateguard.utils.observeToSignInScreen

class UserCompanyInfoFragment: Fragment(R.layout.fragment_user_company_info) {

    private lateinit var binding: FragmentUserCompanyInfoBinding
    private lateinit var viewModel: UserCompanyInfoViewModel
    private lateinit var adapter: UserConstructionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserCompanyInfoBinding.inflate(inflater, container, false)

        binding.constructionList.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(UserCompanyInfoViewModel::class.java)

        adapter = UserConstructionsAdapter(requireContext(), emptyList())
        binding.constructionList.adapter = adapter
        observeToSignInScreen(viewModel.navigateToSignInEvent)
        observeShowFacilityNotFoundToastEvent()

        observeConstructions()
        observeState()

        viewModel.getConstructionsOfFacility()


        return binding.root
    }

    private fun observeConstructions() = viewModel.constructions.observe(viewLifecycleOwner) {
        adapter.setList(it)
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeShowFacilityNotFoundToastEvent() = viewModel.showFacilityNotFoundToastEvent.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        findTopNavController().navigate(R.id.selectFacilityFragment, null, navOptions {
            popUpTo(R.id.selectFacilityFragment) {
                inclusive = true
            }
        })
    }
}