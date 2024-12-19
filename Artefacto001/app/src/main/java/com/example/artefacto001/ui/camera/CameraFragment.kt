package com.example.artefacto001.ui.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artefacto001.R
import com.example.artefacto001.data.retrofit.ApiConfig
import com.example.artefacto001.data.retrofit.DataArtifact
import com.example.artefacto001.data.retrofit.FileUploadResponse
import com.example.artefacto001.databinding.FragmentCameraBinding
import com.example.artefacto001.ui.detail.DetailActivity
import com.example.artefacto001.ui.templearchives.artefacts.ArtefactsAdapter
import com.example.artefacto001.ui.templearchives.artefacts.ArtefactsViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private lateinit var artefactsAdapter: ArtefactsAdapter
    private lateinit var artefactsViewModel: ArtefactsViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        artefactsViewModel = ViewModelProvider(this)[ArtefactsViewModel::class.java]

        setupRecyclerView()

        binding.iconGallery.setOnClickListener { startGallery() }
        binding.iconCamera.setOnClickListener { startCamera() }
        binding.uploadButton.setOnClickListener { uploadImage() }

        loadArtefactData(0)

        return binding.root
    }

    private fun setupRecyclerView() {
        artefactsAdapter = ArtefactsAdapter(mutableListOf()) { artefact ->
            onArtefactClick(artefact)
        }
        binding.artefactsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = artefactsAdapter
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        binding.resultTextView.text = ""
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            Log.d("Image Classification File", "showImage: ${imageFile.path}")
            showLoading(true)
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file", imageFile.name, requestImageFile
            )

            val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEiLCJlbWFpbCI6InVzZXJAZXhhbXBsZS5jb20iLCJpYXQiOjE3MzM3NjMwMDh9.kvdNoe5edSw8G7VgmGf8lBHi6TQudNz8KX1ZaTKQPks"

            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService(ApiConfig.ApiEnvironment.ML_SERVICE)
                    apiService.uploadImage(token, multipartBody).enqueue(object : Callback<FileUploadResponse> {
                        @SuppressLint("SetTextI18n", "DefaultLocale")
                        override fun onResponse(
                            call: Call<FileUploadResponse>,
                            response: Response<FileUploadResponse>
                        ) {
                            if (response.isSuccessful) {
                                val successResponse = response.body()
                                handleUploadResponse(response)
                            } else {
                                binding.resultTextView.text = "Failed to predict"
                                showToast("Failed to get valid response.")
                            }
                            showLoading(false)
                        }

                        override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                            showToast("Request failed: ${t.localizedMessage}")
                            showLoading(false)
                        }
                    })
                } catch (e: HttpException) {
                    showToast("Request failed: ${e.localizedMessage}")
                    showLoading(false)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun handleUploadResponse(response: Response<FileUploadResponse>) {
        if (response.isSuccessful) {
            val successResponse = response.body()
            if (successResponse != null) {
                val isAboveThreshold = successResponse.confidence > 0.70
                val message = if (isAboveThreshold) {
                    "Model berhasil diprediksi"

                } else {
                    "Model berhasil diprediksi, namun berada di bawah 70%."
                }

                val kemungkinan = successResponse.confidence * 100
                val predictPatung = successResponse.prediction
                val artifactScanID = when (successResponse.prediction) {
                    "agastya" -> 1
                    "brahma" -> 2
                    "candra" -> 3
                    "durga" -> 4
                    "ganesa" -> 5
                    "gupolo" -> 6
                    "nandhi" -> 7
                    "siwa" -> 8
                    "surya" -> 9
                    "wisnu" -> 10
                    else -> -1
                }

                loadArtefactData(artifactScanID)

                binding.resultTextView.text = "$message\nKemungkinan Benar: ${"%.2f".format(kemungkinan)}%\nIni adalah patung $predictPatung"
                showToast(message)
            } else {
                binding.resultTextView.text = "No data received."
                showToast("No data received.")
            }
        } else {
            binding.resultTextView.text = "Failed to predict"
            showToast("Failed to get valid response.")
        }
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun onArtefactClick(artefact: DataArtifact) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("EXTRA_ARTEFACT", artefact)
        startActivity(intent)
    }

    private fun loadArtefactData(artifactScanID: Int) {
        val jwtToken = getJwtToken()

        artefactsViewModel.getArtefact(jwtToken).observe(viewLifecycleOwner) { artefactResponse ->
            if (artefactResponse.data.isNotEmpty()) {
                val filteredData = artefactResponse.data.filter { it.artifactID == artifactScanID }

                if (filteredData.isNotEmpty()) {
                    binding.artefactsRecyclerView.visibility = View.VISIBLE
                    artefactsAdapter.updateData(filteredData)
                } else {
                    binding.artefactsRecyclerView.visibility = View.GONE
                    showToast("No matching artifact found.")
                }
            } else {
                binding.artefactsRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun getJwtToken(): String {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJBcmllaXJBQGdtYWlsLmNvbSIsImlhdCI6MTczNDAyNjU0NH0.EEpO6pvC2C88HEcifxDvUw6QMawTKpDgUeqt1JmZixM"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
