package dadm.quixada.ufc.lavandery.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.R

import dadm.quixada.ufc.lavandery.models.Address

class AddressAdapter(
    private val context: Activity,
    private val addressList: ArrayList<Address>
) : ArrayAdapter<Address>(context, R.layout.account_settings_list_item, addressList) {

    private var currentAddressId: String = ""

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.my_address_list_item, null)

        val address = addressList[position]
        val shortAddressTextView: TextView = view.findViewById(R.id.short_address)
        val selectedAddressImageView: ImageView = view.findViewById(R.id.selected_address)
        val btnRemoveAddress: ImageView = view.findViewById(R.id.btn_remove_address)

        shortAddressTextView.text = address.street + ", " + address.number.toString()

        if (address.id == currentAddressId){
            selectedAddressImageView.visibility = View.VISIBLE
        }

        btnRemoveAddress.setOnClickListener {

            if (address.id == currentAddressId){
                Toast.makeText(
                    context,
                    "Não é possível excluir seu endereço atual.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                addressList.remove(addressList[position])
                removeAddress(addressList)
                this.notifyDataSetChanged()
            }
        }

        return view
    }

    private fun removeAddress(addressList: ArrayList<Address>){
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val userId = mAuth.currentUser!!.uid
        val db = Firebase.firestore

        db.collection("users").document(userId)
            .update("addresses", addressList)
            .addOnCompleteListener { task ->
                if(!task.isSuccessful){
                    Toast.makeText(
                        context,
                        "Ocorreu um erro ao remover o endereço.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    fun setCurrentAddress(id: String){
        this.currentAddressId = id
    }
}