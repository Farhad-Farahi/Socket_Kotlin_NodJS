package com.fd.map.data.repository

import com.fd.map.data.remote.TaxiApi
import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val taxiApi: TaxiApi) {
    suspend fun getFeature() =
        taxiApi.getTaxis()
}