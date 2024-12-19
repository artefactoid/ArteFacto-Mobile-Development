package com.example.artefacto001.ui.templearchives

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artefacto001.R
import com.example.artefacto001.data.retrofit.DataCandi
import com.example.artefacto001.ui.templearchives.artefacts.ArtefactsActivity

class TempleArchiveActivity : AppCompatActivity() {
    private lateinit var templeViewModel: TempleViewModel
    private lateinit var templeAdapter: TempleAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyStateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temple_archive)
        supportActionBar?.hide()


        initView()
        initViewModel()
        loadTempleData()
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recycler_events_temple)
        progressBar = findViewById(R.id.progressBar)
        emptyStateTextView = findViewById(R.id.emptyStateTextView)
        val ivBack = findViewById<ImageView>(R.id.ivBackTAr)
        ivBack.setOnClickListener { onBackPressed() }

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        templeAdapter = TempleAdapter(mutableListOf()) { temple ->
            onTempleClick(temple)
        }
        recyclerView.adapter = templeAdapter
    }

    private fun initViewModel() {
        templeViewModel = ViewModelProvider(this).get(TempleViewModel::class.java)
    }

    private fun loadTempleData() {
        val jwtToken = getJwtToken()
        progressBar.visibility = View.VISIBLE
        emptyStateTextView.visibility = View.GONE

        templeViewModel.getTemples(jwtToken).observe(this) { templeResponse ->
            progressBar.visibility = View.GONE
            if (templeResponse.data.isNotEmpty()) {
                recyclerView.visibility = View.VISIBLE
                emptyStateTextView.visibility = View.GONE
                templeAdapter.updateData(templeResponse.data)
            } else {
                recyclerView.visibility = View.GONE
                emptyStateTextView.visibility = View.VISIBLE
                emptyStateTextView.text = getString(R.string.no_temples_found)
            }
        }
    }

    private fun getJwtToken(): String {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJBcmllaXJBQGdtYWlsLmNvbSIsImlhdCI6MTczNDAyNjU0NH0.EEpO6pvC2C88HEcifxDvUw6QMawTKpDgUeqt1JmZixM"
    }

    private fun onTempleClick(temple: DataCandi) {
         val intent = Intent(this, ArtefactsActivity::class.java)
         intent.putExtra("EXTRA_TEMPLE", temple)
         startActivity(intent)
    }
}
