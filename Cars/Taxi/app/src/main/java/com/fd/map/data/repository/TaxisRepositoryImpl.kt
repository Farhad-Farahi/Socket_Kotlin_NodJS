package com.fd.map.data.repository

import com.fd.map.data.remote.BaseApiResponse
import com.fd.map.domain.common.NetworkResult
import com.fd.map.domain.models.TaxiResponse
import com.fd.map.domain.repository.TaxiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class TaxisRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : TaxiRepository, BaseApiResponse() {

    override suspend fun getTaxis(): Flow<NetworkResult<TaxiResponse>> {
        return flow {
            emit(safeApiCall {
                remoteDataSource.getFeature()
            })
        }.flowOn(Dispatchers.IO)
    }
}