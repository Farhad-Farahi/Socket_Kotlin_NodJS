package com.fd.map.data.remote

import com.fd.map.domain.models.TaxiResponse
import retrofit2.Response
import retrofit2.http.GET


interface TaxiApi {

    @GET(EndPoints.TAXIS)
    suspend fun getTaxis(): Response<TaxiResponse>
}