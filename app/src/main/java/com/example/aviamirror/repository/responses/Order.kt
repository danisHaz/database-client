package com.example.aviamirror.repository.responses

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("clientId")
    val clientId: Int,
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
    val price: String,
    @SerializedName("orderTime")
    val orderTime: String,
    @SerializedName("planeFactoryId")
    val planeFactoryId: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("hadLuggage")
    val hadLuggage: Int,
    @SerializedName("timetableId")
    val timetableId: Int,
) {
}