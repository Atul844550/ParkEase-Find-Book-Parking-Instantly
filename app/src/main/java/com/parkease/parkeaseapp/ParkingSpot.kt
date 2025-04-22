package com.parkease.parkeaseapp

import com.google.android.gms.maps.model.LatLng


data class ParkingSpot(
    val name: String,
    val rating: Float,
    val imageResId: Int,
    val info: String,
    val location: LatLng
)
