package com.example.artefacto001.ui.owned_ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.artefacto001.databinding.FragmentOwnedTicketBinding

class OwnedTicketFragment : Fragment() {

    private var _binding: FragmentOwnedTicketBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.hide()
        val ownedTicketViewModel =
            ViewModelProvider(this).get(OwnedTicketViewModel::class.java)


        _binding = FragmentOwnedTicketBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}