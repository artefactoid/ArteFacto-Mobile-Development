package com.example.artefacto001.data.retrofit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TempleResponse(

	@field:SerializedName("data")
	val data: List<DataCandi>,

	@field:SerializedName("status")
	val status: String
)

@Parcelize
data class DataCandi(

	@field:SerializedName("templeID")
	val templeID: Int,

	@field:SerializedName("funfactTitle")
	val funfactTitle: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("funfactDescription")
	val funfactDescription: String
): Parcelable

