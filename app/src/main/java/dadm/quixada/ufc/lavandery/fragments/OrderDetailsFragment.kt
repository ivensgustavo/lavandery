package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.internalModels.LaundryBasketItem
import dadm.quixada.ufc.lavandery.models.Order
import dadm.quixada.ufc.lavandery.models.Address
import java.util.*
import kotlin.collections.ArrayList


class OrderDetailsFragment : Fragment() {

    private lateinit var order: Order

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.changeStatus()
        this.changeBasket()
        this.changeAddress()
        this.changeCollectAndDelivery()
    }

    fun setOrder(order: Order){
        this.order = order
    }

    private fun changeStatus(){
        val orderStatusFragment =
            childFragmentManager.findFragmentById(R.id.order_status_fragment) as OrderStatusFragment

        orderStatusFragment.resolveStatus(order.status)
    }

    private fun changeBasket(){
        val orderBasketFragment =
            childFragmentManager.findFragmentById(R.id.order_basket_fragment) as OrderBasketFragment

        val laundryBasket: ArrayList<LaundryBasketItem> = ArrayList()

        laundryBasket.add(LaundryBasketItem("Camiseta", 7, 3.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Camisa", 2, 5.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Short", 3, 4.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Calças", 7, 3.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Vestido", 2, 5.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Saia", 3, 4.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Casaco", 7, 3.0.toFloat()))

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



}