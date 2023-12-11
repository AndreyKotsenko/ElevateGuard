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
import com.akotsenko.elevateguard.databinding.ItemManagerConstructionsLayoutBinding
import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.screens.adapters.ManagerConstructionAdapter
import com.akotsenko.elevateguard.utils.observeToSignInScreen


class ManagerConstructionsFragment: Fragment(R.layout.fragment_manager_constructions) {

    private lateinit var binding: FragmentManagerConstructionsBinding
    private lateinit var viewModel: ManagerConstructionsViewModel
    private lateinit var adapter: ManagerConstructionAdapter
    private lateinit var addConstructionDialog: AlertDialog
    private lateinit var editConstructionDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerConstructionsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(ManagerConstructionsViewModel::class.java)

        val userRole = viewModel.getUserRole()

        adapter = ManagerConstructionAdapter(emptyList(), userRole) {
            deleteCharacter(it)
        }

        if (userRole == ADMIN_ROLE) {
            binding.addButon.visibility = View.VISIBLE
        } else {
            binding.addButon.visibility = View.GONE
        }

        observeState()
        observeConstructions()
        getConstructions()
        observeToSignInScreen(viewModel.navigateToSignInEvent)

        binding.constructionsList.adapter = adapter
        binding.addButon.setOnClickListener { onAddPressed() }

        binding.constructionsList.setOnItemClickListener { parent, view, position, id ->
            if(userRole == ADMIN_ROLE){
                editConstructionInfo(adapter.getItem(position))
            }
        }

        return binding.root
    }

    private fun getConstructions() {
        viewModel.getConstructions()
    }

    private fun editConstructionInfo(construction: Construction) {
        val dialogBinding = DialogEditConstructionBinding.inflate(layoutInflater)
        dialogBinding.constructionNameEditText.setText(construction.name)
        dialogBinding.edit.setOnClickListener {
            viewModel.updateConstruction(dialogBinding.constructionNameEditText.text.toString(), construction.id.toString())

            if(dialogBinding.constructionNameEditText.text.isNotBlank()){
                viewModel.getConstructions()
                editConstructionDialog.dismiss()
            } else {
                with(dialogBinding) {
                    constructionNameEditText.error = if(constructionNameEditText.text.isBlank()) getString(R.string.field_is_empty) else null
                }
            }
        }

        editConstructionDialog = AlertDialog.Builder(requireContext())
            .setTitle("Edit construction")
            .setView(dialogBinding.root)
            .setNegativeButton("Cancel", null)
            .create()
        editConstructionDialog.show()


    }

    private fun onAddPressed() {
        val dialogBinding = DialogAddConstructionBinding.inflate(layoutInflater)
        dialogBinding.add.setOnClickListener {
            viewModel.createConstruction(dialogBinding.constructionNameEditText.text.toString())

            if(dialogBinding.constructionNameEditText.text.isNotBlank()){
                viewModel.getConstructions()
                addConstructionDialog.dismiss()
            } else {
                with(dialogBinding) {
                    constructionNameEditText.error = if(constructionNameEditText.text.isBlank()) getString(R.string.field_is_empty) else null
                }
            }
        }

        addConstructionDialog = AlertDialog.Builder(requireContext())
            .setTitle("Add construction")
            .setNegativeButton("Cancel", null)
            .setView(dialogBinding.root)
            .create()

        addConstructionDialog.show()
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

    companion object {
        private const val  ADMIN_ROLE = "ADMIN"
    }
}