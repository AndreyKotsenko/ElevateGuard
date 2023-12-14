package com.akotsenko.elevateguard.screens.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navOptions
import com.akotsenko.elevateguard.R
import com.akotsenko.elevateguard.databinding.DialogAddAccidentBinding
import com.akotsenko.elevateguard.databinding.FragmentManagerAccidentBinding
import com.akotsenko.elevateguard.screens.adapters.ManagerAccidentAdapter
import com.akotsenko.elevateguard.utils.findTopNavController
import com.akotsenko.elevateguard.utils.observeEvent
import com.akotsenko.elevateguard.utils.observeToSignInScreen

class ManagerAccidentFragment: Fragment(R.layout.fragment_manager_accident) {

    private lateinit var binding: FragmentManagerAccidentBinding
    private lateinit var viewModel: ManagerAccidentViewModel
    private lateinit var adapter: ManagerAccidentAdapter
    private var constructionNamesToId: Map<String, Int> = emptyMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerAccidentBinding.inflate(inflater, container, false)

        adapter = ManagerAccidentAdapter(emptyList())

        viewModel = ViewModelProvider(this).get(ManagerAccidentViewModel::class.java)

        observeState()
        observeAccidents()
        getAccidents()
        observeConstructions()
        observeShowFacilityNotFoundToastEvent()
        observeShowConstructionNotFoundEvent()
        observeToSignInScreen(viewModel.navigateToSignInEvent)

        binding.accidentList.adapter = adapter
        viewModel.getConstructions()


        return binding.root
    }


    private fun getAccidents() {
        viewModel.getAccidents()
    }


    private fun observeShowFacilityNotFoundToastEvent() = viewModel.showFacilityNotFoundToastEvent.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        findTopNavController().navigate(R.id.selectFacilityFragment, null, navOptions {
            popUpTo(R.id.selectFacilityFragment) {
                inclusive = true
            }
        })
    }

    // we can use this method when we want add new accident
//    private fun onAddPressed() {
//        val dialogBinding = DialogAddAccidentBinding.inflate(layoutInflater)
//        val dialog = AlertDialog.Builder(requireContext())
//            .setTitle("Add construction")
//            .setView(dialogBinding.root)
//            .setPositiveButton("Add") {d, which ->
//                viewModel.createAccident(dialogBinding.constructionSpinner.selectedItem as Int)
//            }
//            .create()
//        dialog.show()
//
//        val keys = constructionNamesToId.keys.toList()
//
//        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, keys)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        dialogBinding.constructionSpinner.adapter = adapter
//
//        dialogBinding.constructionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
//                val constructionId = constructionNamesToId[keys[position]]
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//            }
//        }
//    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeConstructions() = viewModel.constructions.observe(viewLifecycleOwner) {
        constructionNamesToId = it.associate { it.name to it.id } as Map<String, Int>
    }

    private fun observeAccidents() = viewModel.accidents.observe(viewLifecycleOwner) {
        adapter.setList(it)
    }

    private fun observeShowConstructionNotFoundEvent() = viewModel.showConstructionNotFoundToastEvent.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG)
    }

}