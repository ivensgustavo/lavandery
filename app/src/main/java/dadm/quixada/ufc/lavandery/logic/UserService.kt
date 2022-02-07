package dadm.quixada.ufc.lavandery.logic

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dadm.quixada.ufc.lavandery.HomeActivity
import dadm.quixada.ufc.lavandery.models.Address
import dadm.quixada.ufc.lavandery.models.User

class UserService {
    var mAuth = FirebaseAuth.getInstance()
    var db = Firebase.firestore

    fun getUser(id: String, setUser: (user: User?) -> Unit) {
        db.collection("users").document(id)
            .get()
            .addOnSuccessListener { document ->
                val name = document.data!!["name"].toString()
                val surname = document.data!!["surname"].toString()
                val telephone = document.data!!["telephone"].toString()
                val email = document.data!!["email"].toString()
                val accountType = document.data!!["accountType"].toString()
                val profileImageUrl = document.data!!["profileImageUrl"].toString()

                val user = User(
                    id,
                    name,
                    surname,
                    email,
                    telephone,
                    accountType,
                    profileImageUrl
                )
                setUser(user)
            }
            .addOnFailureListener {
                setUser(null)
            }
    }

    fun saveProfileImage(id: String, imageUri: Uri, setResult: (result: Boolean) -> Unit) {
        val filename = id + "_profile_img"
        var storageRef = FirebaseStorage.getInstance().getReference("/images/" + filename)
        storageRef.putFile(imageUri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    db.collection("users").document(id)
                        .update("profileImageUrl", uri.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                setResult(true)
                            } else {
                                setResult(false)
                            }
                        }
                }

            }
            .addOnFailureListener {
                setResult(false)
            }
    }

    fun updateEmail(newEmail: String, password: String, setResult: (result: Boolean) -> Unit) {
        val user = mAuth.currentUser

        val credential = EmailAuthProvider
            .getCredential(user!!.email.toString(), password)

        user.reauthenticate(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.updateEmail(newEmail)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                db.collection("users").document(user.uid)
                                    .update("email", newEmail)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            setResult(true)
                                        } else {
                                            setResult(false)
                                        }
                                    }
                            } else {
                                setResult(false)
                            }
                        }
                } else {
                    setResult(false)
                }
            }
    }

    fun updateFullName(
        name: String,
        surname: String,
        setResult: (result: Boolean) -> Unit
    ) {
        val id = mAuth.currentUser!!.uid
        val documentRef = db.collection("users").document(id)

        db.runBatch { batch ->
            batch.update(documentRef, "name", name)
            batch.update(documentRef, "surname", surname)
        }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setResult(true)
                } else {
                    setResult(false)
                }
            }
    }

    fun updateTelephone(telephone: String, setResult: (result: Boolean) -> Unit){
        val userId = mAuth.currentUser!!.uid

        db.collection("users").document(userId)
            .update("telephone", telephone)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    setResult(true)
                } else {
                    setResult(false)
                }
            }
    }


}