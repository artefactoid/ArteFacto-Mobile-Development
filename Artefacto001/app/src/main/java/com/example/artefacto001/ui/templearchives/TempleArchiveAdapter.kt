package com.example.artefacto001.ui.templearchives

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.artefacto001.R
import com.example.artefacto001.data.retrofit.DataCandi

class TempleAdapter(
    private var temples: MutableList<DataCandi>,
    private val onClick: (DataCandi) -> Unit
) : RecyclerView.Adapter<TempleAdapter.TempleViewHolder>() {

    inner class TempleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        private val progressPercentage: TextView = itemView.findViewById(R.id.progressPercentage)
        private val locationName: TextView = itemView.findViewById(R.id.locationName)
        private val nextButton: ImageView = itemView.findViewById(R.id.nextButton)

        @SuppressLint("SetTextI18n")
        fun bind(temple: DataCandi) {
            Glide.with(itemView.context)
                .load(temple.imageUrl)
                .into(imageView)
            progressBar.progress = 70
            progressPercentage.text = "70%"
            locationName.text = temple.title

            itemView.setOnClickListener { onClick(temple) }
            nextButton.setOnClickListener { onClick(temple) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_candi, parent, false)
        return TempleViewHolder(view)
    }

    override fun onBindViewHolder(holder: TempleViewHolder, position: Int) {
        holder.bind(temples[position])
    }

    override fun getItemCount(): Int = temples.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newTemples: List<DataCandi>) {
        temples.clear()
        temples.addAll(newTemples)
        notifyDataSetChanged()
    }
}
