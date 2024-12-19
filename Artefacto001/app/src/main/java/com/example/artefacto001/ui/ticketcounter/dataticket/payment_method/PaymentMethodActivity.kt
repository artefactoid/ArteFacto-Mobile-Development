package com.example.artefacto001.ui.ticketcounter.dataticket.payment_method

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.artefacto001.R
import com.example.artefacto001.databinding.ActivityPaymentMethodBinding
import com.example.artefacto001.ui.ticketcounter.dataticket.payment_method.paymend.PaymentActivity

class PaymentMethodActivity : AppCompatActivity() {
    private var _binding: ActivityPaymentMethodBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        _binding = ActivityPaymentMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ivBack = findViewById<ImageView>(R.id.ivBackPay)
        ivBack.setOnClickListener { onBackPressed() }

        val rgPaymentMethod = findViewById<RadioGroup>(R.id.rgPaymentMethod)

        rgPaymentMethod.setOnCheckedChangeListener { group, checkedId ->
            for (i in 0 until group.childCount) {
                val view = group.getChildAt(i)
                if (view is RadioButton) {
                    view.setBackgroundResource(R.drawable.radio_button_default)
                }
            }
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            selectedRadioButton.setBackgroundResource(R.drawable.radio_button_checked)
        }

        binding.btnPay.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }

    }
}
