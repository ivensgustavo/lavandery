package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.internalModels.LaundryBasketItem
import dadm.quixada.ufc.lavandery.logic.OrderService
import dadm.quixada.ufc.lavandery.models.Order
import dadm.quixada.ufc.lavandery.models.Address
import java.util.*
import kotlin.collections.ArrayList


class ProviderOrderDetailsFragment : Fragment() {

    private lateinit var order: Order
    private var newStatus: String = ""
    private val orderService = OrderService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_provider_order_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.changeBasket()
        this.changeAddress()
        this.changeCollectAndDelivery()
        this.initializeDropDownStatus(view)

        val currentStatusTextView: TextView = view.findViewById(R.id.status_atual)
        currentStatusTextView.text = this.order.status

        val btnSaveStatus: Button =view.findViewById(R.id.save_status)
        btnSaveStatus.setOnClickListener {
            updateStatus()
        }
    }

    fun setOrder(order: Order){
        this.order = order
    }

    private fun changeBasket(){
        val orderBasketFragment =
            childFragmentManager.findFragmentById(R.id.order_basket_fragment) as OrderBasketFragment

        orderBasketFragment.setOrderDeliveryValue(15.0f)
        orderBasketFragment.setOrderBasket(order.laundryBasket)
    }

    private fun changeAddress(){
        val orderAddressFragment =
            childFragmentManager.findFragmentById(R.id.order_address_fragment) as OrderAddressFragment
        orderAddressFragment.setData(order.addressId, order.consumerId)
    }


    private fun changeCollectAndDelivery(){
        val orderDateAndHourFragment =
            childFragmentManager.findFragmentById(R.id.order_date_and_hour_fragment) as OrderDateAndHourFragment

        orderDateAndHourFragment.setData(
            order.collectionDate,
            order.collectionTime,
            order.deliveryDate,
            order.deliveryTime
        )
    }

    private fun returnToPreviousScreen(){
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun initializeDropDownStatus(view: View){
        val autocompleteDropDownMenu: AutoCompleteTextView = view.findViewById(R.id.new_status_dropdownMenu)
        val statusList: Array<String> = resources.getStringArray(R.array.status)
        val dropdownAdapter: ArrayAdapter<*> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, statusList)
        autocompleteDropDownMenu.setAdapter(dropdownAdapter)

        autocompleteDropDownMenu.setOnItemClickListener { _, _, pos, _ ->
            this.newStatus = statusList[pos]
        }
    }

    private fun updateStatus(){
        orderService.updateStatus(this.order.id, this.newStatus){ result ->
            if(result){
                showSucessMessage()
                returnToPreviousScreen()
            }else{
                showSucessMessage()
            }
        }
    }

    private fun showSucessMessage(){
        Toast.makeText(
            context,
            "Status do pedido atualizado",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showErrorMessage(){
        Toast.makeText(
            context,
            "Ocorreu um erro ao salvar o status do pedido",
            Toast.LENGTH_SHORT
        ).show()
    }



}