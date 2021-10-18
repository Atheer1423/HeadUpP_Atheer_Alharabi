package com.example.headupp
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Context
import android.view.View
import android.view.inputmethod.InputBinding

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


import com.example.headupp.headsupCelebrity
import kotlinx.android.synthetic.main.item_row.view.*

class RVAdapter (val context:Context ,private var celebrities: ArrayList<headsupCelebrity>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: View) : RecyclerView.ViewHolder(binding)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val celebrity = celebrities[position]

        holder.binding.apply {
          tvName.text = celebrity.name
         tvT1.text = celebrity.taboo1
          tvT2.text = celebrity.taboo2
           tvT3.text = celebrity.taboo3
        }
    }

    override fun getItemCount() = celebrities.size

    fun update(celebrities: ArrayList<headsupCelebrity>) {
        this.celebrities = celebrities
        notifyDataSetChanged()
    }
}
