package com.akotsenko.elevateguard.screens.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.akotsenko.elevateguard.databinding.ItemManagerConstructionsLayoutBinding
import com.akotsenko.elevateguard.model.construction.entities.Construction

typealias OnDeletePressedListenerManager = (Construction) -> Unit

class ManagerConstructionAdapter(
    private var constructions: List<Construction>,
    private val OnDeletePressedListener: OnDeletePressedListenerManager
): BaseAdapter(), View.OnClickListener {
    override fun getCount(): Int {
        return constructions.size
    }

    override fun getItem(position: Int): Construction {
        return constructions[position]
    }

    override fun getItemId(position: Int): Long {
        return constructions[position].id!!.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            convertView?.tag as ItemManagerConstructionsLayoutBinding? ?:
            createBinding(parent.context)

        val construction = getItem(position)

        binding.constructionNameTextView.text = construction.name
        binding.deleteImageView.tag = construction

        return binding.root
    }

    private fun createBinding(context: Context): ItemManagerConstructionsLayoutBinding {
        val binding = ItemManagerConstructionsLayoutBinding.inflate(LayoutInflater.from(context))
        binding.deleteImageView.setOnClickListener(this)
        binding.root.tag = binding
        return binding
    }

    override fun onClick(v: View) {
        val construction = v.tag as Construction
        OnDeletePressedListener.invoke(construction)
    }

    fun setList(constructions: List<Construction>) {
        this.constructions = constructions
        notifyDataSetChanged()
    }
}