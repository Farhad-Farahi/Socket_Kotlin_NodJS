package com.hexfa.map.domain.use_case


import com.google.common.truth.Truth.assertThat
import com.hexfa.map.data.repository.FakeTaxisRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetTaxisUseCaseTest {


    private lateinit var getTaxisUseCase: GetTaxisUseCase
    private lateinit var fakeTaxisRepository: FakeTaxisRepository


    @Before
    fun setUp() {
        fakeTaxisRepository = FakeTaxisRepository()
        getTaxisUseCase = GetTaxisUseCase(fakeTaxisRepository)
    }


    @Test
    fun `test response list`() = runBlocking {
        val taxis = getTaxisUseCase.invoke().first()


        assertThat(taxis.data!!.poiList.size == 3).isTrue()
    }

    @Test
    fun `test response list data check with id`() = runBlocking {
        val taxis = getTaxisUseCase.invoke().first()

        assertThat(taxis.data!!.poiList[0].id == 282467).isTrue()
    }

    @Test
    fun `test response list data check with feelType`() = runBlocking {
        val taxis = getTaxisUseCase.invoke().first()

        assertThat(taxis.data!!.poiList[2].fleetType == "POOLING").isFalse()
    }

    @Test
    fun `test response list data check with coordinate`() = runBlocking {
        val taxis = getTaxisUseCase.invoke().first()

        val boolean: Boolean = taxis.data!!.poiList[1].coordinate.latitude == 53.62437730040252 &&
                taxis.data!!.poiList[1].coordinate.longitude == 9.781565603041141

        assertThat(boolean).isTrue()
    }
}