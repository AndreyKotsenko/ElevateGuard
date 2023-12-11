package com.akotsenko.elevateguard.screens.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.akotsenko.elevateguard.databinding.ItemManagerUsersLayoutBinding
import com.akotsenko.elevateguard.model.user.entities.User

typealias OnDeletePressedListenerUsers = (User) -> Unit

class ManagerUsersAdapter(
    private var users: List<User>,
    private val OnDeletePressedListener: OnDeletePressedListenerUsers
): BaseAdapter(), View.OnClickListener {
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
        binding.deleteImageView.visibility = if (user.role == "USER") View.VISIBLE else View.GONE
        binding.deleteImageView.tag = user

        return binding.root
    }

    private fun createBinding(context: Context): ItemManagerUsersLayoutBinding {
        val binding = ItemManagerUsersLayoutBinding.inflate(LayoutInflater.from(context))
        binding.deleteImageView.setOnClickListener(this)
        binding.root.tag = binding
        return binding
    }

    override fun onClick(v: View) {
        val user = v.tag as User
        OnDeletePressedListener.invoke(user)
    }

    fun setList(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }
}