package com.example.artefacto001.ui.ticketcounter.dataticket.payment_method.paymend

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.artefacto001.MainActivity
import com.example.artefacto001.R
import com.example.artefacto001.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    private var _binding: ActivityPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        _binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ivBack = findViewById<ImageView>(R.id.ivBackPayment)
        ivBack.setOnClickListener { onBackPressed() }

        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}