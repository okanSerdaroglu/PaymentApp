package com.okan.paymentapp.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaymentNetworkEntity(

    @SerializedName("applicationID")
    @Expose
    var applicationID: String,

    @SerializedName("sessionID")
    @Expose
    var sessionID: Int,

    @SerializedName("posID")
    @Expose
    var posID: String,

    @SerializedName("returnCode")
    @Expose
    var returnCode: String,

    @SerializedName("returnDesc")
    @Expose
    var returnDesc: String
)