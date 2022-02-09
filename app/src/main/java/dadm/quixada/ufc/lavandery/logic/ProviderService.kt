package dadm.quixada.ufc.lavandery.logic

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.models.Address
import dadm.quixada.ufc.lavandery.models.Provider
import java.util.*
import kotlin.collections.ArrayList

import kotlin.collections.HashMap

class ProviderService {

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
    )
    {
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

                for(document in documents){
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

                    var totalOrdersInWeek = 0
                        orderService.getTotalOrdersInThisWeek(provider.id){ totalOrders ->
                            totalOrdersInWeek = totalOrders
                    }

                   provider.ordersInWeek = totalOrdersInWeek
                    Log.d("Teste", provider.ordersInWeek.toString())

                    providers.add(provider)
                }

                setResult(providers)

            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "Error fetching all providers.")
                setResult(null)
            }
    }


}