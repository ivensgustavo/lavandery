package dadm.quixada.ufc.lavandery.fragments


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import dadm.quixada.ufc.lavandery.HomeActivity

import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.OrdersAdapter
import dadm.quixada.ufc.lavandery.logic.OrderService

import dadm.quixada.ufc.lavandery.models.Order

import kotlin.collections.ArrayList


class MyOrdersFragment : Fragment() {

    private var orderList: ArrayList<Order> = ArrayList()
    private lateinit var ordersAdapter: OrdersAdapter
    private lateinit var orderListView: ListView
    private val orderService = OrderService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_my_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getOrdersFromDB(view)
    }

    private fun initializeViews(view: View) {
        this.ordersAdapter = OrdersAdapter(this.requireActivity(), this.orderList)
        this.orderListView = view.findViewById(R.id.order_list_view)

        this.orderListView.adapter = ordersAdapter

        this.orderListView.setOnItemClickListener { _, _, _, _ ->

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.container_screens, OrderDetailsFragment())
                addToBackStack("Detalhes do Pedido")
                commit()
            }
        }
    }

    private fun getOrdersFromDB(view: View) {

        orderService.getOrders { orders ->
            if(orders != null){
                this.orderList = orders
                this.initializeViews(view)
            }else{
                Toast.makeText(
                    context,
                    "Ocorreu um erro ao buscar seus pedidos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}