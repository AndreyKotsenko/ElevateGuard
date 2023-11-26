package com.akotsenko.elevateguard.screens.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.akotsenko.elevateguard.databinding.ItemManagerAccidentsLayoutBinding
import com.akotsenko.elevateguard.model.accident.entities.Accident

class ManagerAccidentAdapter(private var accidents: List<Accident>): BaseAdapter() {
    override fun getCount(): Int {
        return accidents.size
    }

    override fun getItem(position: Int): Accident {
        return accidents[position]
    }

    override fun getItemId(position: Int): Long {
        return accidents[position].id!!.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = convertView?.tag as ItemManagerAccidentsLayoutBinding? ?: createBinding(parent.context)

        val accident = getItem(position)

        binding.accidentNameTextView.text = accident.date

        return binding.root
    }

    private fun createBinding(context: Context): ItemManagerAccidentsLayoutBinding {
        val binding = ItemManagerAccidentsLayoutBinding.inflate(LayoutInflater.from(context))
        binding.root.tag = binding
        return binding
    }

    fun setList(accidents: List<Accident>) {
        this.accidents = accidents
        notifyDataSetChanged()
    }
}