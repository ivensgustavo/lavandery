package dadm.quixada.ufc.lavandery.internalModels

import java.util.*

class Order(var consumerId: String, var provider_id: String, var qtyItens: Int,var value: Float,
            var creationDate: Date, var collectionDate: Date, var deliveryDate: Date, var status: String) {
}