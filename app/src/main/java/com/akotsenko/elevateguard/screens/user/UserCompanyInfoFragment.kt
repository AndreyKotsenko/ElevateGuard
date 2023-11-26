package com.akotsenko.elevateguard.screens.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akotsenko.elevateguard.*
import com.akotsenko.elevateguard.databinding.FragmentUserCompanyInfoBinding
import com.akotsenko.elevateguard.screens.adapters.UserConstructionsAdapter

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

        observeConstructions()

        viewModel.getConstructionsOfFacility()


        return binding.root
    }

    private fun observeConstructions() = viewModel.constructions.observe(viewLifecycleOwner) {
        adapter.setList(it)
    }
}