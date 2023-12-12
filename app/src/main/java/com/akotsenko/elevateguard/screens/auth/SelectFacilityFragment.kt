package com.akotsenko.elevateguard.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.akotsenko.elevateguard.R
import com.akotsenko.elevateguard.databinding.FragmentSelectFacilityBinding
import com.akotsenko.elevateguard.model.user.entities.FacilityOfUser
import com.akotsenko.elevateguard.screens.adapters.FacilitiesAdapter
import com.akotsenko.elevateguard.utils.observeToSignInScreen

class SelectFacilityFragment: Fragment(R.layout.fragment_select_facility) {

    private lateinit var binding: FragmentSelectFacilityBinding
    private lateinit var viewModel: SelectFacilityViewModel
    private lateinit var adapter: FacilitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectFacilityBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(SelectFacilityViewModel::class.java)

        adapter = FacilitiesAdapter(emptyList())
        binding.facilitiesList.adapter = adapter
        observeToSignInScreen(viewModel.navigateToSignInEvent)
        observeState()
        observeCFacilities()

        viewModel.getFacilities()

        val role = viewModel.getUserRole()

        binding.facilitiesList.setOnItemClickListener { parent, view, position, id ->
            val facilityId = adapter.getItem(position).id
            val facilityName = adapter.getItem(position).name
            viewModel.saveCurrentFacilityId(facilityId)
            viewModel.saveCurrentFacilityName(facilityName)

            if(role == "MANAGER" || role == "ADMIN") {
                findNavController().navigate(R.id.action_selectFacilityFragment_to_managerTabsFragment)
            } else {
                findNavController().navigate(R.id.action_selectFacilityFragment_to_userTabsFragment)
            }

        }

        binding.addFacility.visibility = if(role == "ADMIN") View.VISIBLE else View.GONE
        binding.addFacility.setOnClickListener {
            findNavController().navigate(R.id.action_selectFacilityFragment_to_createFacilityFragment)
        }

        return binding.root
    }

    private fun observeCFacilities() = viewModel.facilities.observe(viewLifecycleOwner) {
        adapter.setList(it)
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }
}