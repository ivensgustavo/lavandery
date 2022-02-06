package dadm.quixada.ufc.lavandery.logic

import android.util.Log

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.models.User

class AuthService {

    val mAuth = FirebaseAuth.getInstance()
    val db = Firebase.firestore

    fun createUser(
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

        val user = User(id, name, surname, email, telephone, accountType, preferences, "-")

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

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        setResult: (result: Boolean) -> Unit
    ) {

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
               setResult(task.isSuccessful)
            }

    }

    fun firebaseAuthWithGoogle(idToken: String, setResult: (result: Boolean) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setResult(true)
                } else {
                    // Quando ocorrer uma falha mostrar uma mensagem ao usuário
                    Log.w("LoginActivty", "signInWithCredential:failure", task.exception)
                    setResult(false)
                }
            }
    }


}