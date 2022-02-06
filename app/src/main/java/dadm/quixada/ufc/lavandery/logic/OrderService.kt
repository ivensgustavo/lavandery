package dadm.quixada.ufc.lavandery.logic

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.internalModels.LaundryBasketItem
import dadm.quixada.ufc.lavandery.models.Order
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class OrderService {

    private val mAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    fun createOrder(
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
        addressId: String,
        setResult: (result: Boolean) -> Unit
    ) {

        val consumerId = mAuth.currentUser!!.uid

        val order = Order(
            consumerId,
            providerId,
            totalItems,
            laundryBasket,
            value,
            creationDate,
            collectionDate,
            deliveryDate,
            status,
            collectionTime,
            deliveryTime,
        )

        order.addressId = addressId

        db.collection("orders").document(order.id)
            .set(order)
            .addOnCompleteListener { task ->
                setResult(task.isSuccessful)
            }
    }


    fun getOrders(setResult: (result: ArrayList<Order>?) -> Unit) {

        val consumerId = mAuth.currentUser!!.uid
        val orders = ArrayList<Order>()

        db.collection("orders").whereEqualTo("consumerId", consumerId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val order = Order(
                        document.data["id"].toString(),
                        document.data["consumerId"].toString(),
                        document.data["providerID"].toString(),
                        document.data["totalItems"].toString().toInt(),
                        document.data["value"].toString().toFloat(),
                        document.data["status"].toString(),
                        (document.data["creationDate"] as Timestamp).toDate(),
                    )

                    order.addressId = document.data["addressId"].toString()

                    orders.add(order)
                }

                setResult(orders)

            }
            .addOnFailureListener {
                setResult(null)
            }
    }

    fun getOrder(id: String, setResult: (result: Order?) -> Unit) {

        db.collection("orders").document(id)
            .get()
            .addOnSuccessListener { document ->

                val laundryBasket = ArrayList<LaundryBasketItem>()
                val list = document.data!!["laundryBasket"] as ArrayList<HashMap<String, Objects>>
                Log.d("Lista de pedidos: ", list.toString())
                list.forEach { item ->
                    val laundryBasketItem = LaundryBasketItem(
                        item["partType"].toString(),
                        item["quantity"].toString().toInt(),
                        item["pricePerPiece"].toString().toFloat()
                    )

                    laundryBasket.add(laundryBasketItem)
                }


                val order =
                    Order(
                        document.data!!["consumerId"].toString(),
                        document.data!!["providerId"].toString(),
                        document.data!!["totalItems"].toString().toInt(),
                        laundryBasket,
                        document.data!!["value"].toString().toFloat(),
                        (document.data!!["creationDate"] as Timestamp).toDate(),
                        (document.data!!["collectionDate"] as Timestamp).toDate(),
                        (document.data!!["deliveryDate"] as Timestamp).toDate(),
                        document.data!!["status"].toString(),
                        document.data!!["collectionTime"].toString(),
                        document.data!!["deliveryTime"].toString(),
                    )

                order.id = id
                order.addressId = document.data!!["addressId"].toString()


                setResult(order)
            }
            .addOnFailureListener {
                setResult(null)
            }
    }

}