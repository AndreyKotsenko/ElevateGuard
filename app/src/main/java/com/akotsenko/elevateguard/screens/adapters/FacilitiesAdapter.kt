package com.akotsenko.elevateguard.screens.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akotsenko.elevateguard.databinding.ItemFacilityLayoutBinding
import com.akotsenko.elevateguard.databinding.ItemManagerUsersLayoutBinding
import com.akotsenko.elevateguard.model.user.entities.FacilityOfUser
import okhttp3.internal.notifyAll

class FacilitiesAdapter(private var facilities: List<FacilityOfUser>): BaseAdapter() {

    fun setList(facilities: List<FacilityOfUser>) {
        this.facilities = facilities
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return facilities.size
    }

    override fun getItem(position: Int): FacilityOfUser {
        return facilities[position]
    }

    override fun getItemId(position: Int): Long {
        return facilities[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            convertView?.tag as ItemFacilityLayoutBinding? ?:
            createBinding(parent.context)

        val facility = getItem(position)

        binding.facilityNameTextView.text = facility.name

        return binding.root
    }

    private fun createBinding(context: Context): ItemFacilityLayoutBinding {
        val binding = ItemFacilityLayoutBinding.inflate(LayoutInflater.from(context))
        binding.root.tag = binding
        return binding
    }
}