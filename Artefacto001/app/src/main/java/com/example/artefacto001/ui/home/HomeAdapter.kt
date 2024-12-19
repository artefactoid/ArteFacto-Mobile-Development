package com.example.artefacto001.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artefacto001.R
import com.example.artefacto001.databinding.CardPromoBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.EventViewHolder>() {

    private var eventList: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = CardPromoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val eventItem = eventList[position]
        holder.binding.cardImage.setImageResource(R.drawable.sale)
    }

    override fun getItemCount(): Int = eventList.size

    fun submitList(list: List<String>) {
        eventList = list
        notifyDataSetChanged()
    }

    class EventViewHolder(val binding: CardPromoBinding) : RecyclerView.ViewHolder(binding.root)
}
