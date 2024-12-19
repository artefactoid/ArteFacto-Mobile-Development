package com.example.artefacto001.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artefacto001.databinding.FragmentHomeBinding
import com.example.artefacto001.ui.notifikasi.NotificationsActivity
import com.example.artefacto001.ui.templearchives.TempleArchiveActivity
import com.example.artefacto001.ui.ticketcounter.TicketCounterActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.hide()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.notificationIcon.setOnClickListener {
            val intent = Intent(requireContext(), NotificationsActivity::class.java)
            startActivity(intent)
        }

        binding.ticketCounter.setOnClickListener {
            val intent = Intent(requireContext(), TicketCounterActivity::class.java)
            startActivity(intent)
        }

        binding.templeArchives.setOnClickListener {
            val intent = Intent(requireContext(), TempleArchiveActivity::class.java)
            startActivity(intent)
        }

        val recyclerView = binding.recyclerEvents
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val adapter = HomeAdapter()
        recyclerView.adapter = adapter

        val listItems = List(5) { "Event Item" }
        adapter.submitList(listItems)

        val recyclerView2 = binding.recyclerUnwatched
        recyclerView2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val adapter2 = HomeAdapter()
        recyclerView2.adapter = adapter2

        val listItems2 = List(5) { "Event Item" }
        adapter2.submitList(listItems2)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
