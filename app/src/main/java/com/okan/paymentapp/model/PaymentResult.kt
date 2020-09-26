package com.okan.paymentapp.model

data class PaymentResult(
    var applicationID: String,
    var sessionID: Int,
    var posID: String,
    var returnCode: String,
    var returnDesc: String
)


