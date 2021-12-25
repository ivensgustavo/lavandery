package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.OrdersAdapter
import dadm.quixada.ufc.lavandery.internalModels.Order
import java.util.*
import kotlin.collections.ArrayList

class MyRequestsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderList: ArrayList<Order> = ArrayList()

        orderList.add(Order("SGG47HHDG6", "GHDN78DH4G", 30,
            70.0.toFloat(),collectionDate = Date(2021,12, 20),
            Date(2021, 12, 21), status = "Pendente"))

        orderList.add(Order("SGG47HGDG6", "GH7N78DH4G", 30,
            70.0.toFloat(),collectionDate = Date(2021,12, 20),
            Date(2021, 12, 21), status = "Entregue"))

        val ordersAdapter = OrdersAdapter(this.requireActivity(), orderList)
        val orderListView: ListView = view.findViewById(R.id.order_list_view)

        orderListView.adapter = ordersAdapter
    }
}