package com.example.aviamirror.repository.responses

import com.google.gson.annotations.SerializedName

data class TakeoffResponse(
    @SerializedName("timetable_id")
    val timetableId: Int,
    @SerializedName("city_name_from")
    val cityFrom: String,
    @SerializedName("city_name_to")
    val cityTo: String,
    @SerializedName("time_from")
    val timeFrom: String,
    @SerializedName("time_to")
    val timeTo: String,
)