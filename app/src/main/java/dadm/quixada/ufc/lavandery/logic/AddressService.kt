package dadm.quixada.ufc.lavandery.logic

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.models.Address

class AddressService {

    var mAuth = FirebaseAuth.getInstance()
    var db = Firebase.firestore

    fun addAddress(id: String, street: String, number: Int, cep:Int, complement: String, setResult: (result: Boolean) -> Unit){

        val address = Address(id, street, number, cep, complement)

        db.collection("users").document(mAuth.currentUser!!.uid)
            .collection("addresses").document(address.id).set(address)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    db.collection("users").document(mAuth.currentUser!!.uid)
                        .update("current_address_id", address.id)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                setResult(true)
                            }else {
                                setResult(false)
                            }
                        }
                } else {
                    setResult(false)
                }
            }
    }

    fun getAllAddresses(setResult: (result: ArrayList<Address>?) -> Unit){
        val userRef = db.collection("users").document(mAuth.currentUser!!.uid)
        val addressRef =userRef.collection("addresses")
        val addressList = ArrayList<Address>()

        addressRef.get()
            .addOnSuccessListener { documents ->
                for(document in documents) {
                    addressList.add(
                        Address(
                            document.data["id"].toString(),
                            document.data["street"].toString(),
                            document.data["number"].toString().toInt(),
                            document.data["cep"].toString().toInt(),
                            document.data["complement"].toString()
                        )
                    )
                }

                setResult(addressList)
            }
            .addOnFailureListener {
                setResult(null)
            }
    }

    fun updateCurrentAddress(id: String, setResult: (result: Boolean) -> Unit){
        val docRef = db.collection("users").document(mAuth.currentUser!!.uid)

        docRef.update("current_address_id", id)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setResult(true)
                }else {
                    setResult(false)
                }
            }
    }

    fun getCurrentAddress(setResult: (result: Address?) -> Unit){

        val docRef = db.collection("users").document(mAuth.currentUser!!.uid)

        docRef.get()
            .addOnSuccessListener { document ->
                val currentAddressId = document.data!!["current_address_id"].toString()

                docRef.collection("addresses").document(currentAddressId)
                    .get()
                    .addOnSuccessListener { addressDocument ->
                        val street = addressDocument.data!!["street"].toString()
                        val number = addressDocument.data!!["number"].toString().toInt()
                        val cep = addressDocument.data!!["cep"].toString().toInt()
                        val complement = addressDocument.data!!["complement"].toString()

                        val address = Address(currentAddressId, street, number, cep, complement)
                        setResult(address)
                    }
                    .addOnFailureListener {
                        setResult(null)
                    }
            }
            .addOnFailureListener {
                setResult(null)
            }
    }

    fun getAddress(id: String, consumerId: String, setResult: (result: Address?) -> Unit){

        val docRef = db.collection("users").document(consumerId)

        docRef.get()
            .addOnSuccessListener {

                docRef.collection("addresses").document(id)
                    .get()
                    .addOnSuccessListener { addressDocument ->
                        val street = addressDocument.data!!["street"].toString()
                        val number = addressDocument.data!!["number"].toString().toInt()
                        val cep = addressDocument.data!!["cep"].toString().toInt()
                        val complement = addressDocument.data!!["complement"].toString()

                        val address = Address(id, street, number, cep, complement)
                        setResult(address)
                    }
                    .addOnFailureListener {
                        setResult(null)
                    }
            }
            .addOnFailureListener {
                setResult(null)
            }
    }

}