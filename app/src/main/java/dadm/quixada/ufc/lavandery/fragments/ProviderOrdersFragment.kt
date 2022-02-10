package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import dadm.quixada.ufc.lavandery.HomeActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.ProviderOrdersAdapter
import dadm.quixada.ufc.lavandery.logic.OrderService
import dadm.quixada.ufc.lavandery.logic.ProviderService
import dadm.quixada.ufc.lavandery.models.Order


class ProviderOrdersFragment : Fragment() {

    private var orderList: ArrayList<Order> = ArrayList()
    private lateinit var providerOrdersAdapter: ProviderOrdersAdapter
    private lateinit var orderListView: ListView
    private val providerService = ProviderService()
    private val orderService = OrderService()
    private var screenTitle: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_provider_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getOrdersFromDB(view)
    }

    private fun initializeViews(view: View) {
        this.providerOrdersAdapter = ProviderOrdersAdapter(this.requireActivity(), this.orderList)
        this.orderListView = view.findViewById(R.id.order_list_view)

        this.orderListView.adapter = this.providerOrdersAdapter

        this.orderListView.setOnItemClickListener { _, _, position, _ ->
            openOrderDetails(this.orderList[position].id)
        }

        this.screenTitle =  arguments?.get("screenTitle") as String
        val screenTitleTextView: TextView = view.findViewById(R.id.screen_title)
        screenTitleTextView.text = this.screenTitle
    }

    private fun getOrdersFromDB(view: View) {

        val orderStatus = arguments?.get("orderStatus") as String

        providerService.getOrders(orderStatus) { orders ->
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

    private fun openOrderDetails(orderId: String){

        if(this.screenTitle == "Novos pedidos"){
            openAcceptFragment(orderId)
        }else{
            openOrderUpdateStatusFragment(orderId)
        }
    }

    private fun openAcceptFragment(orderId: String){
        orderService.getOrder(orderId) { result ->
            if (result != null) {
                val  acceptRequestFragment = AcceptRequestFragment()
                acceptRequestFragment.setOrder(result)

                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.container_provider_screens, acceptRequestFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    private fun openOrderUpdateStatusFragment(orderId: String){
        orderService.getOrder(orderId) { result ->
            if (result != null) {
                val  updateStatusFragment = ProviderOrderDetailsFragment()
                updateStatusFragment.setOrder(result)

                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.container_provider_screens, updateStatusFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

}