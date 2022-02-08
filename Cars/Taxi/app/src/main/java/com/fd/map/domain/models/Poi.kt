package com.fd.map.domain.models

data class Poi(
    val coordinate: Coordinate,
    val fleetType: String,
    val heading: Double,
    val id: Int
)