package com.fd.map.domain.repository

import com.fd.map.domain.common.NetworkResult
import com.fd.map.domain.models.TaxiResponse
import kotlinx.coroutines.flow.Flow


interface TaxiRepository {
    suspend fun getTaxis(): Flow<NetworkResult<TaxiResponse>>
}