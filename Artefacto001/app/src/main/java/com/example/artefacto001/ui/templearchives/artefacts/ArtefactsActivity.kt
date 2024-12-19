package com.example.artefacto001.ui.templearchives.artefacts
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.artefacto001.R
import com.example.artefacto001.data.retrofit.DataArtifact
import com.example.artefacto001.ui.detail.DetailActivity

class ArtefactsActivity : AppCompatActivity() {

    private lateinit var artefactsViewModel: ArtefactsViewModel
    private lateinit var templeImageView: ImageView
    private lateinit var templeTitleTextView: TextView
    private lateinit var templeDescriptionTextView: TextView
    private lateinit var funFactTitleTextView: TextView
    private lateinit var funFactDescriptionTextView: TextView
    private lateinit var locationButton: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var artifactsRecyclerView: RecyclerView
    private lateinit var artefactsAdapter: ArtefactsAdapter
    private lateinit var emptyStateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_artefacts)
        supportActionBar?.hide()
        val ivBack = findViewById<ImageView>(R.id.ivBackArte)
        ivBack.setOnClickListener { onBackPressed() }

        templeImageView = findViewById(R.id.ivTemplesImage)
        templeTitleTextView = findViewById(R.id.tvTemplesTitle)
        templeDescriptionTextView = findViewById(R.id.tvTemplesDescription)
        funFactTitleTextView = findViewById(R.id.tvFunFactTitle)
        funFactDescriptionTextView = findViewById(R.id.tvFunFactTemple)
        locationButton = findViewById(R.id.locationButton)

        initView()
        initViewModel()
        loadArtefactData()

        artefactsViewModel.templeData.observe(this, Observer { temple ->
            temple?.let {
                Glide.with(this)
                    .load(it.imageUrl)
                    .into(templeImageView)

                templeTitleTextView.text = it.title
                templeDescriptionTextView.text = it.description
                funFactTitleTextView.text = it.funfactTitle
                funFactDescriptionTextView.text = it.funfactDescription

                locationButton.setOnClickListener {
                    val url = temple.location
                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url))
                    startActivity(intent)
                }
            }
        })


        artefactsViewModel.loading.observe(this, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        artefactsViewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {

            }
        })

        val templeID = intent.getIntExtra("TEMPLE_ID", 1)

        artefactsViewModel.fetchTempleData(templeID, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJBcmllaXJBQGdtYWlsLmNvbSIsImlhdCI6MTczNDAyNjU0NH0.EEpO6pvC2C88HEcifxDvUw6QMawTKpDgUeqt1JmZixM")

        artefactsViewModel.getArtefact( "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJBcmllaXJBQGdtYWlsLmNvbSIsImlhdCI6MTczNDAyNjU0NH0.EEpO6pvC2C88HEcifxDvUw6QMawTKpDgUeqt1JmZixM")
    }

    private fun initView() {
        progressBar = findViewById(R.id.progressBar)
        artifactsRecyclerView = findViewById(R.id.rvArtefacts)
        emptyStateTextView = findViewById(R.id.emptyStateTextView)

        artifactsRecyclerView.layoutManager = LinearLayoutManager(this)
        artefactsAdapter = ArtefactsAdapter(mutableListOf()) { artefact ->
            onArtefactClick(artefact)
        }
        artifactsRecyclerView.adapter = artefactsAdapter
    }

    private fun initViewModel() {
        artefactsViewModel = ViewModelProvider(this).get(ArtefactsViewModel::class.java)
    }

    private fun loadArtefactData() {
        val jwtToken = getJwtToken()
        progressBar.visibility = View.VISIBLE
        emptyStateTextView.visibility = View.GONE

        artefactsViewModel.getArtefact(jwtToken).observe(this) { artefactResponse ->
            progressBar.visibility = View.GONE
            if (artefactResponse.data.isNotEmpty()) {
                artifactsRecyclerView.visibility = View.VISIBLE
                emptyStateTextView.visibility = View.GONE
                artefactsAdapter.updateData(artefactResponse.data)
            } else {
                artifactsRecyclerView.visibility = View.GONE
                emptyStateTextView.visibility = View.VISIBLE
                emptyStateTextView.text = getString(R.string.no_temples_found)
            }
        }
    }

    private fun getJwtToken(): String {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJBcmllaXJBQGdtYWlsLmNvbSIsImlhdCI6MTczNDAyNjU0NH0.EEpO6pvC2C88HEcifxDvUw6QMawTKpDgUeqt1JmZixM"
    }

    private fun onArtefactClick(artefact: DataArtifact) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("EXTRA_ARTEFACT", artefact)
        startActivity(intent)
    }

}
