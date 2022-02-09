package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.internalModels.LaundryBasketItem
import dadm.quixada.ufc.lavandery.logic.OrderService
import dadm.quixada.ufc.lavandery.models.Order
import dadm.quixada.ufc.lavandery.models.Address
import java.util.*
import kotlin.collections.ArrayList


class AcceptRequestFragment : Fragment() {

    private lateinit var order: Order
    private val orderService = OrderService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accept_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.changeBasket()
        this.changeAddress()
        this.changeCollectAndDelivery()

        val btnAccept: Button = view.findViewById(R.id.btn_accept_request)
        btnAccept.setOnClickListener {
            acceptOrder()
        }

        val btnReject: Button = view.findViewById(R.id.btn_reject_request)
        btnReject.setOnClickListener {
            rejectOrder()
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

    private fun acceptOrder(){
        orderService.updateStatus(this.order.id, "Agendado"){ result ->
            if(result){
               returnToPreviousScreen()
            }else{
                this.showErrorAcceptOrder()
            }
        }
    }

    private fun rejectOrder(){
        orderService.removeOrder(this.order.id){ result ->
            if(result) {
                returnToPreviousScreen()
            }else{
                this.showErrorRejectOrder()
            }
        }
    }

    private fun returnToPreviousScreen(){
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun showErrorAcceptOrder(){
        Toast.makeText(
            context,
            "Ocorreu um erro com a aceitação do pedido",
            Toast.LENGTH_SHORT
            ).show()
    }

    private fun showErrorRejectOrder(){
        Toast.makeText(
            context,
            "Ocorreu um erro com a rejeição do pedido",
            Toast.LENGTH_SHORT
        ).show()
    }

}