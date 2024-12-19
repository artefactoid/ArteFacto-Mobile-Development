package com.example.artefacto001.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.artefacto001.R
import com.example.artefacto001.data.retrofit.DataArtifact

class DetailActivity : AppCompatActivity() {

    private lateinit var artifact: DataArtifact
    private lateinit var imageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var funFactTitleTextView: TextView
    private lateinit var funFactDescriptionTextView: TextView
    private lateinit var locationButton: ImageButton
    private lateinit var periodeTextView: TextView
    private lateinit var materialTextView: TextView
    private lateinit var sizeTextView: TextView
    private lateinit var styleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.hide()

        imageView = findViewById(R.id.imageView)
        titleTextView = findViewById(R.id.titleArtefact)
        descriptionTextView = findViewById(R.id.descriptionArtefact)
        periodeTextView = findViewById(R.id.periodArtefact)
        materialTextView = findViewById(R.id.materialArtefact)
        sizeTextView = findViewById(R.id.sizeArtefact)
        styleTextView = findViewById(R.id.styleArtefact)
        funFactTitleTextView = findViewById(R.id.title_funfact)
        funFactDescriptionTextView = findViewById(R.id.description_funfact)
        locationButton = findViewById(R.id.locateButton)

        artifact = intent.getParcelableExtra("EXTRA_ARTEFACT")!!

        Log.d("DetailActivity", "Title: ${artifact.title}")
        Log.d("DetailActivity", "Description: ${artifact.description}")
        Log.d("DetailActivity", "Location: ${artifact.location}")

        Glide.with(this)
            .load(artifact.imageUrl)
            .into(imageView)

        titleTextView.text = artifact.title
        descriptionTextView.text = artifact.description
        funFactTitleTextView.text = artifact.funfactTitle
        funFactDescriptionTextView.text = artifact.funfactDescription
        periodeTextView.text = artifact.detailPeriod
        materialTextView.text = artifact.detailMaterial
        sizeTextView.text = artifact.detailSize
        styleTextView.text = artifact.detailStyle

        locationButton.setOnClickListener {
            val url = artifact.location
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}
