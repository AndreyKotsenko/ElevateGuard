package com.akotsenko.elevateguard.screens.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akotsenko.elevateguard.R
import com.akotsenko.elevateguard.model.construction.entities.Construction

class UserConstructionsAdapter(private val context: Context, private var constructionsList: List<Construction>) :
    RecyclerView.Adapter<UserConstructionsAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val constructionsNameTextView: TextView = itemView.findViewById(R.id.constructionsNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val construction = constructionsList.get(position)
        holder.constructionsNameTextView.text = construction.name
    }

    override fun getItemCount(): Int {
        return constructionsList.size
    }

    fun setList(constructionsList: List<Construction>) {
        this.constructionsList = constructionsList
        notifyDataSetChanged()
    }

}