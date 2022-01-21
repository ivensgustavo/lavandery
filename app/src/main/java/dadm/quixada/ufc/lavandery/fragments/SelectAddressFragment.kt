package dadm.quixada.ufc.lavandery.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.AddressRegistrationActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.AddressAdapter
import dadm.quixada.ufc.lavandery.models.Address
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SelectAddressFragment : BottomSheetDialogFragment() {

    private var addressList: ArrayList<Address> = ArrayList()
    private var currentAddressId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_address, container, false)
    }

    private fun getAddressesFromDb(view: View){
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val userId = mAuth.currentUser!!.uid
        val db = Firebase.firestore

        val docRef = db.collection("users").document(userId)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val addresses = document.data!!["addresses"] as ArrayList<HashMap<String, *>>
                    for(address in addresses){
                        this.addressList.add(
                            Address(
                                address["id"].toString(),
                                address["street"].toString(),
                                address["number"].toString().toInt(),
                                address["cep"].toString().toInt(),
                                address["complement"].toString()
                            )
                        )
                    }

                    this.currentAddressId = document.data!!["current_address_id"].toString()
                    initializeViews(view)
                }
            }
            .addOnFailureListener { _ ->
                Toast.makeText(
                    context,
                    "Ocorreu um erro ao buscar os seus endereços",
                    Toast.LENGTH_SHORT
                ).show()
            }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAddressesFromDb(view)
    }

    private fun initializeViews(view: View){
        val addressListView: ListView = view.findViewById(R.id.my_address_list_view)
        val adapter = AddressAdapter(requireActivity(), this.addressList)
        adapter.setCurrentAddress(this.currentAddressId)
        addressListView.adapter = adapter

        addressListView.setOnItemClickListener { _, _, position, _ ->
            this.currentAddressId = this.addressList[position].id
            updateCurrentAddress()
            adapter.notifyDataSetChanged()
            this.dismiss()
        }

        val btnAddNewAddress: Button = view.findViewById(R.id.btn_add_address)

        btnAddNewAddress.setOnClickListener {
            val intent = Intent(context, AddressRegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateCurrentAddress(){
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val userId = mAuth.currentUser!!.uid
        val db = Firebase.firestore

        val docRef = db.collection("users").document(userId)

        docRef.update("current_address_id", this.currentAddressId)
            .addOnCompleteListener { task ->
                if(!task.isSuccessful){
                    Toast.makeText(
                        context,
                        "Ocorreu um erro ao atualizar o endereço atual",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}