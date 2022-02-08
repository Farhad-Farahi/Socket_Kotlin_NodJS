package com.fd.map.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fd.map.domain.common.NetworkResult
import com.fd.map.domain.models.TaxiResponse
import com.fd.map.domain.use_case.GetTaxisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel  @Inject constructor(
    private val getTaxisUseCase: GetTaxisUseCase
    ) : ViewModel(){

    private val _taxis : MutableStateFlow<NetworkResult<TaxiResponse>> = MutableStateFlow(NetworkResult.Loading())
    val taxis : StateFlow<NetworkResult<TaxiResponse>> =_taxis


    init {
        getTaxis()
    }

    private fun getTaxis() = viewModelScope.launch {
        getTaxisUseCase.invoke().collect {
            _taxis.value = it
        }
    }

    fun onManualRefresh() {
        getTaxis()
    }

}