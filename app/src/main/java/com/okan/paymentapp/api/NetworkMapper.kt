package com.okan.paymentapp.api

import com.okan.paymentapp.model.PaymentResult
import com.okan.paymentapp.util.EntityMapper
import javax.inject.Inject

class NetworkMapper
@Inject
constructor() : EntityMapper<PaymentNetworkEntity, PaymentResult> {

    override fun mapFromEntity(entity: PaymentNetworkEntity): PaymentResult {
        return PaymentResult(
            applicationID = entity.applicationID,
            sessionID = entity.sessionID,
            posID = entity.posID,
            returnCode = entity.returnCode,
            returnDesc = entity.returnDesc
        )
    }

    override fun mapToEntity(domainModel: PaymentResult): PaymentNetworkEntity {
        return PaymentNetworkEntity(
            applicationID = domainModel.applicationID,
            sessionID = domainModel.sessionID,
            posID = domainModel.posID,
            returnCode = domainModel.returnCode,
            returnDesc = domainModel.returnDesc
        )
    }
}