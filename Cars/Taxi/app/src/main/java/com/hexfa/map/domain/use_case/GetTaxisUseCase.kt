package com.hexfa.map.domain.use_case


import com.hexfa.map.domain.common.NetworkResult
import com.hexfa.map.domain.models.TaxiResponse
import com.hexfa.map.domain.repository.TaxiRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject


class GetTaxisUseCase @Inject constructor(
    private val repository: TaxiRepository
) {
    suspend operator fun invoke(): Flow<NetworkResult<TaxiResponse>> {
        return repository.getTaxis()
    }
}
