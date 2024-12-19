package com.example.artefacto001.ui.ticketcounter.dataticket

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artefacto001.R
import com.example.artefacto001.databinding.ActivityDataTicketBinding
import com.example.artefacto001.ui.ticketcounter.dataticket.payment_method.PaymentMethodActivity
import java.util.Calendar

class DataTicketActivity : AppCompatActivity() {
    private var _binding: ActivityDataTicketBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvDate: TextView
    private lateinit var ivCalendar: ImageView
    private lateinit var tvAmount: TextView
    private lateinit var ivUp: ImageView
    private lateinit var ivDown: ImageView
    private lateinit var tvTotalPrice: TextView
    private lateinit var tvTotalTicket: TextView
    private var ticketAmount = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()

        _binding = ActivityDataTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedItem = intent.getStringExtra("selected_item")

        val recyclerView = binding.recyclerEventsData
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter = DataTicketAdapter()
        recyclerView.adapter = adapter

        val listItems = List(5) { index ->
            selectedItem?.let { "$it - Event $index" } ?: "Event $index"
        }
        adapter.submitList(listItems)

        tvDate = findViewById(R.id.tvDate)
        ivCalendar = findViewById(R.id.ivCalendar)
        tvAmount = findViewById(R.id.tvAmount)
        ivUp = findViewById(R.id.ivUp)
        ivDown = findViewById(R.id.ivDown)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        tvTotalTicket = findViewById(R.id.tvTotalTicket)

        ivCalendar.setOnClickListener { showDatePicker() }

        ivUp.setOnClickListener { incrementTicketAmount() }
        ivDown.setOnClickListener { decrementTicketAmount() }

        binding.btnBuyNow.setOnClickListener {
            val intent = Intent(this, PaymentMethodActivity::class.java)
            startActivity(intent)
        }
        val ivBack = findViewById<ImageView>(R.id.ivBackDT)
        ivBack.setOnClickListener { onBackPressed() }

    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                tvDate.text = formattedDate
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun incrementTicketAmount() {
        ticketAmount++
        updateAmountTextView()
    }

    private fun decrementTicketAmount() {
        if (ticketAmount > 1) {
            ticketAmount--
        }
        updateAmountTextView()
    }

    @SuppressLint("SetTextI18n")
    private fun updateAmountTextView() {
        tvAmount.text = ticketAmount.toString()
        tvAmount.text = "$ticketAmount ticket"
        tvTotalTicket.text = "$ticketAmount ticket"
        tvTotalPrice.text = "Rp. ${ticketAmount * 50000}"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
