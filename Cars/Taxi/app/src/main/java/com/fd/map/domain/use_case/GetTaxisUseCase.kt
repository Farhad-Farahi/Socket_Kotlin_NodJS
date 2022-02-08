package com.fd.map.domain.use_case


import com.fd.map.domain.common.NetworkResult
import com.fd.map.domain.models.TaxiResponse
import com.fd.map.domain.repository.TaxiRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject


class GetTaxisUseCase @Inject constructor(
    private val repository: TaxiRepository
) {
    suspend operator fun invoke(): Flow<NetworkResult<TaxiResponse>> {
        return repository.getTaxis()
    }
}
