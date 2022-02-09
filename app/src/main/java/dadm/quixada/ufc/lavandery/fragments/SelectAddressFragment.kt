package dadm.quixada.ufc.lavandery.fragments

import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dadm.quixada.ufc.lavandery.AddressRegistrationActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.AddressAdapter
import dadm.quixada.ufc.lavandery.logic.AddressService
import dadm.quixada.ufc.lavandery.models.Address
import kotlin.collections.ArrayList


class SelectAddressFragment : BottomSheetDialogFragment() {

    private var addressList: ArrayList<Address> = ArrayList()
    private var currentAddressId: String = ""
    private val addressService = AddressService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_address, container, false)
    }

    private fun getAddressesFromDb(view: View) {

        addressService.getAllAddresses { result ->
            if(result != null){
                this.addressList = result
                addressService.getCurrentAddress { currentAddress ->
                    if(currentAddress != null){
                        this.currentAddressId = currentAddress.id
                        this.initializeViews(view)
                    }
                }
                this.initializeViews(view)
            }else{
                Toast.makeText(
                    requireActivity(),
                    "Ocorreu um erro ao buscar os endereços.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAddressesFromDb(view)
    }

    private fun initializeViews(view: View) {
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
            intent.putExtra("accountType", "Consumidor")
            startActivity(intent)
        }
    }

    private fun updateCurrentAddress() {

        addressService.updateCurrentAddress(this.currentAddressId){ result ->
            if(!result){
                Toast.makeText(
                    context,
                    "Ocorreu um erro ao atualizar o endereço atual.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}