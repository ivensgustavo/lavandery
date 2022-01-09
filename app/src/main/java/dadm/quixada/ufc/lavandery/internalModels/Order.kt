package dadm.quixada.ufc.lavandery.internalModels

import java.util.*

class Order(var consumerId: String, var provider_id: String, var qtyItens: Int,var value: Float,
            var creationDate: Date, var collectionDate: Date, var deliveryDate: Date, var status: String) {

    constructor(consumerId: String, provider_id: String, qtyItens: Int,value: Float,
                creationDate: Date, collectionDate: Date, deliveryDate: Date, status: String, collectionTime: String,
    deliveryTime: String): this(consumerId, provider_id, qtyItens, value, creationDate, collectionDate, deliveryDate, status)
}