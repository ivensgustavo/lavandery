package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.logic.AddressService
import dadm.quixada.ufc.lavandery.models.Address


class OrderAddressFragment : Fragment() {

    private lateinit var localTextView: TextView
    private lateinit var complementTextView: TextView
    private lateinit var cepTextView: TextView
    private val addressService = AddressService()

    private var addressId = "-"
    private var consumerId = "-"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
    }

    private fun initializeViews(view: View) {
        localTextView = view.findViewById(R.id.order_details_address_local)
        complementTextView = view.findViewById(R.id.order_details_address_complement)
        cepTextView = view.findViewById(R.id.order_details_address_cep)
    }

    fun setData(addressId: String, consumerId: String) {
        this.addressId = addressId
        this.consumerId = consumerId
        this.getAddressFromDB()
    }

    private fun updateScreenWithAddressData(address: Address) {
        localTextView.text = address.street+","+address.number.toString()
        complementTextView.text = address.complement
        cepTextView.text = address.cep.toString()
    }

    private fun getAddressFromDB(){
        addressService.getAddress(this.addressId, this.consumerId){ result ->
            if(result != null){
                updateScreenWithAddressData(result)
            }else{
                Toast.makeText(
                    requireActivity(),
                    "Erro ao buscar endereço do pedido",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

}