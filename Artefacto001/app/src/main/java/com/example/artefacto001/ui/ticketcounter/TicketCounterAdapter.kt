package com.example.artefacto001.ui.ticketcounter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artefacto001.databinding.ItemArtefactBinding

class TicketCounterAdapter(
    private val images: List<Int>,
    private val onItemClickListener: (String) -> Unit
) : RecyclerView.Adapter<TicketCounterAdapter.EventViewHolder>() {

    private var eventList: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemArtefactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val eventItem = eventList[position]


        if (position < images.size) {
            holder.binding.cardImage.setImageResource(images[position])
        } else {
            holder.binding.cardImage.setImageResource(android.R.color.darker_gray)
        }

        holder.binding.root.setOnClickListener {
            onItemClickListener(eventItem)
        }
    }

    override fun getItemCount(): Int = eventList.size

    fun submitList(list: List<String>) {
        eventList = list
        notifyDataSetChanged()
    }

    class EventViewHolder(val binding: ItemArtefactBinding) : RecyclerView.ViewHolder(binding.root)
}
