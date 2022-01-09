package dadm.quixada.ufc.lavandery.internalModels

import java.util.*
import kotlin.collections.ArrayList

class Order(
    var consumerId: String, var providerId: String, var totalItems: Int, var value: Float,
    var creationDate: Date, var collectionDate: Date, var deliveryDate: Date, var status: String
) {

    var collectionTime: String = ""
    var deliveryTime: String = ""
    var laundryBasket: ArrayList<LaundryBasketItem> = ArrayList()

    constructor(
        consumerId: String,
        providerId: String,
        totalItems: Int,
        laundryBasket: ArrayList<LaundryBasketItem>,
        value: Float,
        creationDate: Date,
        collectionDate: Date,
        deliveryDate: Date,
        status: String,
        collectionTime: String,
        deliveryTime: String
    ) : this(
        consumerId,
        providerId,
        totalItems,
        value,
        creationDate,
        collectionDate,
        deliveryDate,
        status
    ){
        this.laundryBasket = laundryBasket
        this.collectionTime = collectionTime
        this.deliveryTime = deliveryTime
    }
}