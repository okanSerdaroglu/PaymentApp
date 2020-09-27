package com.okan.paymentapp.repository

import com.okan.paymentapp.api.NetworkMapper
import com.okan.paymentapp.api.PaymentAPI
import com.okan.paymentapp.model.PaymentResult
import com.okan.paymentapp.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    private val paymentAPI: PaymentAPI,
    private val networkMapper: NetworkMapper
) {
    suspend fun sendPayment(): Flow<DataState<PaymentResult>> = flow {
        emit(DataState.Loading)
        try {
            val paymentResult = paymentAPI.sendPaymentRequest()
            emit(DataState.Success(networkMapper.mapFromEntity(paymentResult)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}