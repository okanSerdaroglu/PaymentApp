package com.okan.paymentapp.api

import retrofit2.http.POST

interface PaymentAPI  {

    @POST ("payment")
    suspend fun sendPaymentRequest():PaymentNetworkEntity

}