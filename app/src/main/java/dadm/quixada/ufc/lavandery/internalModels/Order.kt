package dadm.quixada.ufc.lavandery.internalModels

import java.util.*

class Order(var consumerId: String, var provider_id: String, var qtyParts: Int,var value: Float,
            var collectionDate: Date, var deliveryDate: Date, var status: String) {
}