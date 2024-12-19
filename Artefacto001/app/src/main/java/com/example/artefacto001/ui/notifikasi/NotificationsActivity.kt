package com.example.artefacto001.ui.notifikasi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.artefacto001.MainActivity
import com.example.artefacto001.databinding.ActivityNotificationsBinding

class NotificationsActivity : AppCompatActivity() {
    private var _binding: ActivityNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        enableEdgeToEdge()
        _binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackNotif.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}