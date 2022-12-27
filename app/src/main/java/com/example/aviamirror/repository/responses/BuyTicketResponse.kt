package com.example.aviamirror.repository.responses

import com.google.gson.annotations.SerializedName

data class BuyTicketResponse(
    @SerializedName("timeFrom")
    val timeFrom: String,
    @SerializedName("timeTo")
    val timeTo: String,
    @SerializedName("cityCodeFrom")
    val cityCodeFrom: Int,
    @SerializedName("cityCodeTo")
    val cityCodeTo: Int,
    @SerializedName("seatNumberId")
    val seatNumberId: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("extraLuggageAmount")
    val extraLuggageAmount: Int,
    @SerializedName("planeFactoryId")
    val planeFactoryId: Int,
    @SerializedName("cityNameFrom")
    val cityNameFrom: String,
    @SerializedName("cityNameTo")
    val cityNameTo: String,
) {
}