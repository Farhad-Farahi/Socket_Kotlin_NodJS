package com.hexfa.map.domain.repository

import com.hexfa.map.domain.common.NetworkResult
import com.hexfa.map.domain.models.TaxiResponse
import kotlinx.coroutines.flow.Flow


interface TaxiRepository {
    suspend fun getTaxis(): Flow<NetworkResult<TaxiResponse>>
}