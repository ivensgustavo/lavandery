package dadm.quixada.ufc.lavandery.internalModels

import java.time.LocalDate

class Order(var consumerId: String, var provider_id: String, var qtyItens: Int,var value: Float,
            var creationDate: LocalDate, var collectionDate: LocalDate, var deliveryDate: LocalDate, var status: String) {
}