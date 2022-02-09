package dadm.quixada.ufc.lavandery.models

import android.os.Parcelable
import dadm.quixada.ufc.lavandery.internalModels.LaundryBasketItem
import java.util.*
import kotlin.collections.ArrayList

class Order(
    var consumerId: String,
    var providerId: String,
    var totalItems: Int,
    var value: Float,
    var status: String
) {

    var id = UUID.randomUUID().toString()
    var laundryBasket = ArrayList<LaundryBasketItem>()
    var creationDate = Date()
    lateinit var collectionDate: Date
    lateinit var deliveryDate: Date
    var collectionTime = ""
    var deliveryTime = ""
    var addressId = "-"

    constructor(
        id: String,
        consumerId: String,
        providerId: String,
        totalItems: Int,
        value: Float,
        status: String,
        creationDate: Date
    ) : this(
        consumerId,
        providerId,
        totalItems,
        value,
        status
    ) {
        this.id = id
        this.creationDate = creationDate
    }

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
        deliveryTime: String,
    ) : this(
        consumerId,
        providerId,
        totalItems,
        value,
        status
    ) {
        this.laundryBasket = laundryBasket
        this.creationDate = creationDate
        this.collectionDate = collectionDate
        this.deliveryDate = deliveryDate
        this.collectionTime = collectionTime
        this.deliveryTime = deliveryTime
    }

    constructor(
                id: String,
                consumerId: String,
                providerId: String,
                totalItems: Int,
                value: Float,
                status: String,
                collectionDate: Date,
                deliveryDate: Date,
                collectionTime: String,
                deliveryTime: String
    ): this(consumerId, providerId, totalItems, value, status){
        this.id = id
        this.collectionDate = collectionDate
        this.deliveryDate = deliveryDate
        this.collectionTime = collectionTime
        this.deliveryTime = deliveryTime
    }
}