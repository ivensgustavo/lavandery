package dadm.quixada.ufc.lavandery.fragments

import android.os.Build
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.annotation.RequiresApi
import dadm.quixada.ufc.lavandery.HomeActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.OrdersAdapter
import dadm.quixada.ufc.lavandery.internalModels.Order
import java.time.LocalDate
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderList: ArrayList<Order> = ArrayList()

        orderList.add(Order(
            "SGG47HHDG6",
            "GHDN78DH4G", 30,
            70.0.toFloat(),
            LocalDate.parse("2021-12-17"),
            LocalDate.parse("2021-12-18"),
            LocalDate.parse("2021-12-19"),
            "Lavando"))

        orderList.add(Order(
            "SGG47HGDG6",
            "GH7N78DH4G",
            30,
            70.0.toFloat(),
            LocalDate.parse("2021-12-13"),
            LocalDate.parse("2021-12-14"),
            LocalDate.parse("2021-12-15"),
            "Entregue"))

        orderList.add(Order(
            "SGG47HGDG6",
            "GH7N78DH4G",
            30,
            70.0.toFloat(),
            LocalDate.parse("2021-12-08"),
            LocalDate.parse("2021-12-09"),
            LocalDate.parse("2021-12-10"),
            "Agendado"))

        orderList.add(Order(
            "SGG47HGDG6",
            "GH7N78DH4G",
            30,
            70.0.toFloat(),
            LocalDate.parse("2021-12-04"),
            LocalDate.parse("2021-12-05"),
            LocalDate.parse("2021-12-06"),
            "Transportando"))

        val ordersAdapter = OrdersAdapter(this.requireActivity(), orderList)
        val orderListView: ListView = view.findViewById(R.id.order_list_view)

        orderListView.adapter = ordersAdapter

        orderListView.setOnItemClickListener { _, _, _, _ ->

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.container_screens, OrderDetails())
                addToBackStack("Detalhes")
                commit()
            }
        }
    }
}