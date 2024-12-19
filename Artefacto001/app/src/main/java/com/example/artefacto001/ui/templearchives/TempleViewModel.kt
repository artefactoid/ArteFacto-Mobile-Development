package com.example.artefacto001.ui.templearchives
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.artefacto001.data.retrofit.ApiConfig
import com.example.artefacto001.data.retrofit.TempleResponse
import kotlinx.coroutines.Dispatchers

class TempleViewModel : ViewModel() {
    private val apiService = ApiConfig.getTempleApi()

    fun getTemples(jwtToken: String) = liveData<TempleResponse>(Dispatchers.IO) {
        try {
            val response = apiService.getTemples("Bearer $jwtToken")
            if (response.isSuccessful && response.body() != null) {
                emit(response.body()!!)
            } else {
                emit(TempleResponse(status = "error", data = emptyList()))
            }
        } catch (e: Exception) {
            emit(TempleResponse(status = "error", data = emptyList()))
        }
    }
}
