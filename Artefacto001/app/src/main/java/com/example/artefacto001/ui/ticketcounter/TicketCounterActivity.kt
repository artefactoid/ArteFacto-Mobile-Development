package com.example.artefacto001.ui.ticketcounter

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artefacto001.R
import com.example.artefacto001.databinding.ActivityTicketCounterBinding
import com.example.artefacto001.ui.ticketcounter.dataticket.DataTicketActivity

class TicketCounterActivity : AppCompatActivity() {
    private var _binding: ActivityTicketCounterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()

        _binding = ActivityTicketCounterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ivBack = findViewById<ImageView>(R.id.ivBackTC)
        ivBack.setOnClickListener { onBackPressed() }

        val imageList = listOf(
            R.drawable.prambanan,
            R.drawable.soon
        )
        val listItems = listOf(
            "Artefact 1",
            "Artefact 2"
        )

        val adapter = TicketCounterAdapter(imageList) { eventItem ->
            val intent = Intent(this, DataTicketActivity::class.java)
            intent.putExtra("selected_item", eventItem)
            startActivity(intent)
        }

        binding.rvArtefacts.layoutManager = LinearLayoutManager(this)
        binding.rvArtefacts.adapter = adapter

        adapter.submitList(listItems)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
