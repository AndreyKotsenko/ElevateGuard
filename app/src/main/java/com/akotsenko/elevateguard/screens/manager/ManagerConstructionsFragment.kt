package com.akotsenko.elevateguard.screens.manager

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akotsenko.elevateguard.R
import com.akotsenko.elevateguard.databinding.DialogAddConstructionBinding
import com.akotsenko.elevateguard.databinding.DialogEditConstructionBinding
import com.akotsenko.elevateguard.databinding.FragmentManagerConstructionsBinding
import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.screens.adapters.ManagerConstructionAdapter


class ManagerConstructionsFragment: Fragment(R.layout.fragment_manager_constructions) {

    private lateinit var binding: FragmentManagerConstructionsBinding
    private lateinit var viewModel: ManagerConstructionsViewModel
    private lateinit var adapter: ManagerConstructionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerConstructionsBinding.inflate(inflater, container, false)

        adapter = ManagerConstructionAdapter(emptyList()) {
            deleteCharacter(it)
        }

        viewModel = ViewModelProvider(this).get(ManagerConstructionsViewModel::class.java)

        observeState()
        observeConstructions()
        getConstructions()

        binding.constructionsList.adapter = adapter
        binding.addButon.setOnClickListener { onAddPressed() }

        binding.constructionsList.setOnItemClickListener { parent, view, position, id ->
            editConstructionInfo(adapter.getItem(position))
        }

        return binding.root
    }

    private fun getConstructions() {
        viewModel.getConstructions()
    }

    private fun editConstructionInfo(construction: Construction) {
        val dialogBinding = DialogEditConstructionBinding.inflate(layoutInflater)
        dialogBinding.constructionNameEditText.setText(construction.name)

        val listener = DialogInterface.OnClickListener { dialog, which ->
            if(which == DialogInterface.BUTTON_POSITIVE) {

                viewModel.updateConstruction(dialogBinding.constructionNameEditText.text.toString(), construction.id.toString())
            }
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Edit construction")
            .setView(dialogBinding.root)
            .setPositiveButton("Edit", listener)
            .setNegativeButton("Cancel", listener)
            .create()
        dialog.show()


    }

    private fun onAddPressed() {
        val dialogBinding = DialogAddConstructionBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add construction")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") {d, which ->
                viewModel.createConstruction(dialogBinding.constructionNameEditText.text.toString())
                viewModel.getConstructions()
            }
            .create()
        dialog.show()
    }

    private fun deleteCharacter(construction: Construction) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if(which == DialogInterface.BUTTON_POSITIVE) {

                viewModel.deleteConstruction(construction.id.toString())
                viewModel.getConstructions()
            }
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete construction")
            .setMessage("Are you sure do you to delete the construction")
            .setPositiveButton("Delete", listener)
            .setNegativeButton("Cancel", listener)
            .create()
        dialog.show()
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeConstructions() = viewModel.constructions.observe(viewLifecycleOwner) {
        adapter.setList(it)
    }
}