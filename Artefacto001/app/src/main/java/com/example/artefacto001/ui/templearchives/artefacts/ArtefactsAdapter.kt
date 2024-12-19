package com.example.artefacto001.ui.templearchives.artefacts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.artefacto001.R
import com.example.artefacto001.data.retrofit.DataArtifact

class ArtefactsAdapter(
    private var artefact: MutableList<DataArtifact>,
    private val onClick: (DataArtifact) -> Unit
) : RecyclerView.Adapter<ArtefactsAdapter.ArtefactViewHolder>() {

    inner class ArtefactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.card_image)
        private val textView: TextView = itemView.findViewById(R.id.card_title)
        private val textView1: TextView = itemView.findViewById(R.id.card_description)

        @SuppressLint("SetTextI18n")
        fun bind(artefact: DataArtifact) {
            Glide.with(itemView.context)
                .load(artefact.imageUrl)
                .into(imageView)
            textView.text = artefact.title
            textView1.text = artefact.funfactDescription

            itemView.setOnClickListener { onClick(artefact) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtefactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_artefact, parent, false)
        return ArtefactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtefactViewHolder, position: Int) {
        holder.bind(artefact[position])
    }

    override fun getItemCount(): Int = artefact.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newArtefacts: List<DataArtifact>) {
        artefact.clear()
        artefact.addAll(newArtefacts)
        notifyDataSetChanged()
    }
}
