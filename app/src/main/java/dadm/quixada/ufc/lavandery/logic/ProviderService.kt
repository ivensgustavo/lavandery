package dadm.quixada.ufc.lavandery.logic

import android.content.ContentValues
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.models.Address
import dadm.quixada.ufc.lavandery.models.Order
import dadm.quixada.ufc.lavandery.models.Provider
import java.util.*
import kotlin.collections.ArrayList

import kotlin.collections.HashMap

class ProviderService {

    private val mAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private val orderService = OrderService()

    fun createProvider(
        id: String,
        name: String,
        surname: String,
        email: String,
        telephone: String,
        accountType: String,
        setResult: (result: Boolean) -> Unit
    ) {
        val preferences = HashMap<String, Boolean>()
        preferences["ironed_clothes"] = false
        preferences["orders_at_reception"] = false
        preferences["scented_clothes"] = false
        preferences["t_shirts_on_hanger"] = false

        val profileImg = "-"

        val provider = Provider(id, name, surname, email, telephone, accountType, profileImg)

        db.collection("users").document(id)
            .set(provider)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setResult(true)
                } else {
                    setResult(false)
                }
            }
    }

    fun getAllProviders(setResult: (result: ArrayList<Provider>?) -> Unit) {
        db.collection("users").whereEqualTo("accountType", "Prestador de serviços de lavanderia")
            .get()
            .addOnSuccessListener { documents ->

                val providers = ArrayList<Provider>()

                for (document in documents) {
                    val id = document.data["id"].toString()
                    val name = document.data["name"].toString()
                    val surname = document.data["surname"].toString()
                    val telephone = document.data["telephone"].toString()
                    val email = document.data["email"].toString()
                    val accountType = document.data["accountType"].toString()
                    val profileImageUrl = document.data["profileImageUrl"].toString()
                    val addressData = document.data["address"] as HashMap<String, Objects>

                    val address = Address(
                        addressData["id"].toString(),
                        addressData["street"].toString(),
                        addressData["number"].toString().toInt(),
                        addressData["cep"].toString().toInt(),
                        addressData["complement"].toString(),
                        addressData["latitude"].toString().toDouble(),
                        addressData["longitude"].toString().toDouble()
                    )

                    val provider = Provider(
                        id,
                        name,
                        surname,
                        email,
                        telephone,
                        accountType,
                        profileImageUrl,
                        address
                    )

                    orderService.getTotalOrdersInThisWeek(provider.id) { totalOrders ->
                        provider.ordersInWeek = totalOrders
                    }

                    providers.add(provider)
                }

                setResult(providers)

            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "Error fetching all providers.")
                setResult(null)
            }
    }

    fun getOrders(status: String, setResult: (result: ArrayList<Order>?) -> Unit) {

        val providerId = mAuth.currentUser!!.uid
        val orders = ArrayList<Order>()

        db.collection("orders").whereEqualTo("providerId", providerId)
            .whereEqualTo("status", status).orderBy("creationDate", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val order = Order(
                        document.data["id"].toString(),
                        document.data["consumerId"].toString(),
                        document.data["providerId"].toString(),
                        document.data["totalItems"].toString().toInt(),
                        document.data!!["value"].toString().toFloat(),
                        document.data!!["status"].toString(),
                        (document.data!!["collectionDate"] as Timestamp).toDate(),
                        (document.data!!["deliveryDate"] as Timestamp).toDate(),
                        document.data!!["collectionTime"].toString(),
                        document.data!!["deliveryTime"].toString(),
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


}