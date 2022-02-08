package com.hexfa.map.data.repository

import com.hexfa.map.domain.common.NetworkResult
import com.hexfa.map.domain.models.Coordinate
import com.hexfa.map.domain.models.Poi
import com.hexfa.map.domain.models.TaxiResponse
import com.hexfa.map.domain.repository.TaxiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeTaxisRepository : TaxiRepository  {

    companion object{
        //make fake data
        fun getData(taxResponse : TaxiResponse): NetworkResult.Success<TaxiResponse> {
            return NetworkResult.Success(data = taxResponse)
        }

        private val coordinate1 = Coordinate(latitude = 53.6843411185656 , longitude = 10.034848053311075)
        private val coordinate2 = Coordinate(latitude = 53.62437730040252 , longitude = 9.781565603041141)
        private val coordinate3 = Coordinate(latitude = 53.68867273056803 , longitude =  9.92748058763996)

        private val poi1 = Poi(id = 282467 , coordinate = coordinate1 , fleetType = "POOLING" , heading = 313.6428108968617)
        private val poi2 = Poi(id = 465146 , coordinate = coordinate2 , fleetType = "POOLING" , heading = 218.88890911604426)
        private val poi3 = Poi(id = 604239 , coordinate = coordinate3 , fleetType = "TAXI" , heading = 10.998014160452263)

        private val poiList : List<Poi> = listOf(poi1 , poi2, poi3)
        private val taxiResponse = TaxiResponse(poiList)

        val network : NetworkResult<TaxiResponse> = getData(taxiResponse)

    }

     override suspend fun getTaxis(): Flow<NetworkResult<TaxiResponse>> {
       return flow {
           emit(network)
       }
    }
}

