package dadm.quixada.ufc.lavandery.logic

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.AddressRegistrationActivity
import dadm.quixada.ufc.lavandery.models.Consumer
import dadm.quixada.ufc.lavandery.models.User

class ConsumerService {

    private val db = Firebase.firestore
    private val mAuth = FirebaseAuth.getInstance()

    fun createConsumer(
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

        val user =
            Consumer(id, name, surname, email, telephone, accountType, preferences, profileImg)

        db.collection("users").document(id)
            .set(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setResult(true)
                } else {
                    setResult(false)
                }
            }
    }
}