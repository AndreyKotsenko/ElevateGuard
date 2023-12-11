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
import com.akotsenko.elevateguard.databinding.DialogAddUserBinding
import com.akotsenko.elevateguard.databinding.DialogEditUserBinding
import com.akotsenko.elevateguard.databinding.DialogShowInfoBinding
import com.akotsenko.elevateguard.databinding.FragmentManagerUsersBinding
import com.akotsenko.elevateguard.model.auth.entities.RegisterData
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.screens.adapters.ManagerUsersAdapter
import com.akotsenko.elevateguard.utils.observeToSignInScreen

class ManagerUsersFragment : Fragment(R.layout.fragment_manager_users) {

    private lateinit var binding: FragmentManagerUsersBinding
    private lateinit var viewModel: ManagerUsersViewModel
    private lateinit var adapter: ManagerUsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerUsersBinding.inflate(inflater, container, false)

        adapter = ManagerUsersAdapter(emptyList()) {
            deleteCharacter(it)
        }

        viewModel = ViewModelProvider(this).get(ManagerUsersViewModel::class.java)

        observeToSignInScreen(viewModel.navigateToSignInEvent)
        observeState()
        observeUsers()

        viewModel.getUsers()

        binding.usersList.adapter = adapter
        binding.addButton.setOnClickListener { onAddPressed() }

        binding.usersList.setOnItemClickListener { parent, view, position, id ->
            if(adapter.getItem(position).role == "MANAGER" || adapter.getItem(position).role == "ADMIN"){
                showUserInfo(adapter.getItem(position))
            } else {
                editUserInfo(adapter.getItem(position))
            }
        }


        return binding.root
    }

    private fun onAddPressed() {
        val dialogBinding = DialogAddUserBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add user")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") {d, which ->
                val registerData = toRegisterData(dialogBinding)

                viewModel.createUser(registerData)
                viewModel.getUsers()

            }
            .create()
        dialog.show()
    }

    private fun showUserInfo(user: User) {
        val dialogBinding = DialogShowInfoBinding.inflate(layoutInflater)
        dialogBinding.firstNameEditText.setText(user.firstName)
        dialogBinding.lastNameEditText.setText(user.lastName)
        dialogBinding.emailEditText.setText(user.email)
        dialogBinding.mobileEditText.setText(user.mobile)
        dialogBinding.roleEditText.setText(user.role)
        dialogBinding.emailNotificationCheckBox.isChecked = user.isReceiveEmailNotification != 0
        dialogBinding.pushNotificationCheckBox.isChecked = user.isReceivePushNotification != 0
        dialogBinding.smsNotificationCheckBox.isChecked = user.isReceiveSmsNotification != 0

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Info")
            .setView(dialogBinding.root)
            .setPositiveButton("Ok") {d, which ->

            }
            .create()
        dialog.show()
    }

    private fun editUserInfo(user: User) {
        val dialogBinding = DialogEditUserBinding.inflate(layoutInflater)
        dialogBinding.firstNameEditText.setText(user.firstName)
        dialogBinding.lastNameEditText.setText(user.lastName)
        dialogBinding.emailEditText.setText(user.email)
        dialogBinding.mobileEditText.setText(user.mobile)
        dialogBinding.roleSpinner.setSelection(if(user.role == "USER") 0 else 1)
        dialogBinding.emailNotificationCheckBox.isChecked = user.isReceiveEmailNotification != 0
        dialogBinding.pushNotificationCheckBox.isChecked = user.isReceivePushNotification != 0
        dialogBinding.smsNotificationCheckBox.isChecked = user.isReceiveSmsNotification != 0


        val listener = DialogInterface.OnClickListener { dialog, which ->
            if(which == DialogInterface.BUTTON_POSITIVE) {
                val userForUpdate = toUser(dialogBinding)
                viewModel.updateUser(user.id.toString(), userForUpdate, dialogBinding.passwordEditText.text.toString())
                viewModel.getUsers()
            }
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Edit user")
            .setView(dialogBinding.root)
            .setPositiveButton("Edit", listener)
            .setNegativeButton("Cancel", listener)
            .create()
        dialog.show()


    }

    private fun deleteCharacter(user: User) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if(which == DialogInterface.BUTTON_POSITIVE) {
                viewModel.deleteUser(user.id.toString())
                viewModel.getUsers()
            }
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete character")
            .setMessage("Are you sure do you to delete the character")
            .setPositiveButton("Delete", listener)
            .setNegativeButton("Cancel", listener)
            .create()
        dialog.show()
    }

    private fun toRegisterData(dialogBinding: DialogAddUserBinding): RegisterData {
        with(dialogBinding){
            return RegisterData(
                userFirstName = firstNameEditText.text.toString(),
                userLastName = lastNameEditText.text.toString(),
                userEmail = emailEditText.text.toString(),
                userPassword = passwordEditText.text.toString(),
                userMobile = mobileEditText.text.toString(),
                userRole = roleSpinner.selectedItem.toString(),
                userIsReceiveEmailNotification = if(emailNotificationCheckBox.isChecked) 1 else 0,
                userIsReceiveSmsNotification = if(smsNotificationCheckBox.isChecked) 1 else 0,
                userIsReceivePushNotification = if(pushNotificationCheckBox.isChecked) 1 else 0,
                userFacilityId = viewModel.getCurrentFacilityId().toInt()
            )
        }
    }

    private fun toUser(dialogBinding: DialogEditUserBinding): User {
        with(dialogBinding){
            return User(
                id = null,
                firstName = firstNameEditText.text.toString(),
                lastName = lastNameEditText.text.toString(),
                positionId = null,
                facilityId = viewModel.getCurrentFacilityId().toInt(),
                email = emailEditText.text.toString(),
                mobile = mobileEditText.text.toString(),
                role = roleSpinner.selectedItem.toString(),
                isReceiveEmailNotification = if(emailNotificationCheckBox.isChecked) 1 else 0,
                isReceiveSmsNotification = if(smsNotificationCheckBox.isChecked) 1 else 0,
                isReceivePushNotification = if(pushNotificationCheckBox.isChecked) 1 else 0
            )
        }
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeUsers() = viewModel.users.observe(viewLifecycleOwner) {
        adapter.setList(it)
    }

}