package com.akotsenko.elevateguard.screens.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.akotsenko.elevateguard.databinding.ItemManagerUsersLayoutBinding
import com.akotsenko.elevateguard.model.user.entities.User

class UserUsersAdapter(private var users: List<User>): BaseAdapter() {
    override fun getCount(): Int {
        return users.size
    }

    override fun getItem(position: Int): User {
        return users[position]
    }

    override fun getItemId(position: Int): Long {
        return users[position].id!!.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            convertView?.tag as ItemManagerUsersLayoutBinding? ?:
            createBinding(parent.context)

        val user = getItem(position)

        binding.firstNameTextView.text = user.firstName
        binding.lastNameTextView.text = user.lastName
        binding.deleteImageView.visibility = View.GONE

        return binding.root
    }

    private fun createBinding(context: Context): ItemManagerUsersLayoutBinding {
        val binding = ItemManagerUsersLayoutBinding.inflate(LayoutInflater.from(context))
        binding.root.tag = binding
        return binding
    }

    fun setList(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }
}