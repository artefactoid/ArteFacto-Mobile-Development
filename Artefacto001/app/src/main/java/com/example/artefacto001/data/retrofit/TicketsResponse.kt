package com.example.artefacto001.data.retrofit

import com.google.gson.annotations.SerializedName

data class TicketsResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("tickets")
	val tickets: List<TicketsItem>
)

data class TicketsItem(

	@field:SerializedName("templeID")
	val templeID: Int,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("quota")
	val quota: Int,

	@field:SerializedName("temple_name")
	val templeName: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("ticketID")
	val ticketID: Int,

	@field:SerializedName("location_url")
	val locationUrl: String
)
