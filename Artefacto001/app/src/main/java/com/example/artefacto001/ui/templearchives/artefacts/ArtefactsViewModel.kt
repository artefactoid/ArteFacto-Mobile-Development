package com.example.artefacto001.ui.templearchives.artefacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.artefacto001.data.retrofit.ApiConfig
import com.example.artefacto001.data.retrofit.ApiServiceArtefact
import com.example.artefacto001.data.retrofit.ApiServiceCandi
import com.example.artefacto001.data.retrofit.ArtifactsResponse
import com.example.artefacto001.data.retrofit.DataCandi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtefactsViewModel : ViewModel() {

    private val _templeData = MutableLiveData<DataCandi?>()
    val templeData: LiveData<DataCandi?> get() = _templeData


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchTempleData(templeID: Int, jwtToken: String) {
        _loading.value = true

        val apiService: ApiServiceCandi = ApiConfig.getTempleApi()

        viewModelScope.launch {
            try {
                val response = apiService.getTemples("Bearer $jwtToken")

                _loading.value = false

                if (response.isSuccessful) {
                    val temples = response.body()?.data
                    val temple = temples?.find { it.templeID == templeID }
                    _templeData.value = temple
                } else {
                    _error.value = "Error fetching temple data"
                }
            } catch (e: Exception) {
                _loading.value = false
                _error.value = e.message
            }
        }
    }

    fun getArtefact(jwtToken: String) = liveData<ArtifactsResponse>(Dispatchers.IO) {

        val apiService: ApiServiceArtefact = ApiConfig.getArtefactApi()

        try {
            val response = apiService.getArtifacts(1,"Bearer $jwtToken")
            if (response.isSuccessful && response.body() != null) {
                emit(response.body()!!)
            } else {
                emit(ArtifactsResponse(status = "error", data = emptyList()))
            }
        } catch (e: Exception) {
            emit(ArtifactsResponse(status = "error", data = emptyList()))
        }
    }
}
