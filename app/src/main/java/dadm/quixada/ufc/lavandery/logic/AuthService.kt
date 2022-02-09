package dadm.quixada.ufc.lavandery.logic

import android.util.Log

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthService {

    val mAuth = FirebaseAuth.getInstance()

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
                    Log.w("LoginActivty", "signInWithCredential:failure", task.exception)
                    setResult(false)
                }
            }
    }


}