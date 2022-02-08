package com.hexfa.map.data.remote

import com.hexfa.map.domain.models.TaxiResponse
import retrofit2.Response
import retrofit2.http.GET


interface TaxiApi {

    @GET(EndPoints.TAXIS)
    suspend fun getTaxis(): Response<TaxiResponse>
}