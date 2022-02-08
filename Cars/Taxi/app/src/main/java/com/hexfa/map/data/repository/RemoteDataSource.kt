package com.hexfa.map.data.repository

import com.hexfa.map.data.remote.TaxiApi
import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val taxiApi: TaxiApi) {
    suspend fun getFeature() =
        taxiApi.getTaxis()
}