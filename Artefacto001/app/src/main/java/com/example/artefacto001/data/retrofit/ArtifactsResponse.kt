package com.example.artefacto001.data.retrofit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ArtifactsResponse(

	@field:SerializedName("data")
	val data: List<DataArtifact>,

	@field:SerializedName("status")
	val status: String
)

@Parcelize
data class DataArtifact(

	@field:SerializedName("detail_size")
	val detailSize: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("temple_id")
	val templeId: Int,

	@field:SerializedName("is_read")
	val isRead: Int,

	@field:SerializedName("detail_period")
	val detailPeriod: String,

	@field:SerializedName("detail_material")
	val detailMaterial: String,

	@field:SerializedName("funfact_title")
	val funfactTitle: String,

	@field:SerializedName("artifactID")
	val artifactID: Int,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("detail_style")
	val detailStyle: String,

	@field:SerializedName("is_bookmark")
	val isBookmark: Int,

	@field:SerializedName("funfact_description")
	val funfactDescription: String
): Parcelable

