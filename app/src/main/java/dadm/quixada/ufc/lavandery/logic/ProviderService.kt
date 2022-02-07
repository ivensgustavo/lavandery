package dadm.quixada.ufc.lavandery.logic

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.models.User
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ProviderService {

    private val db = Firebase.firestore


    fun getAllProviders(setResult: (result: ArrayList<User>?) -> Unit){
        val providers = ArrayList<User>()

        db.collection("users")
            .whereEqualTo("accountType", "Provedor de serviços de lavanderia")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    val id = document.data["id"].toString()
                    val name = document.data["name"].toString()
                    val surname = document.data["surname"].toString()
                    val telephone = document.data["telephone"].toString()
                    val email = document.data["email"].toString()
                    val accountType = document.data["accountType"].toString()
                    val preferences = document.data["preferences"] as HashMap<String, Boolean>
                    val profileImageUrl = document.data["profileImageUrl"].toString()

                    val user = User(
                        id,
                        name,
                        surname,
                        email,
                        telephone,
                        accountType,
                        preferences,
                        profileImageUrl
                    )

                    providers.add(user)
                }

                setResult(providers)
            }
            .addOnFailureListener {
                setResult(null)
            }
    }




}