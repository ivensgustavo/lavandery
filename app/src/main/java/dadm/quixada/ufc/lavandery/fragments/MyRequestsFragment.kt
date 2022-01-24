package dadm.quixada.ufc.lavandery.fragments


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.OrdersAdapter

import dadm.quixada.ufc.lavandery.internalModels.Order

import kotlin.collections.ArrayList


class MyRequestsFragment : Fragment() {

    private var orderList: ArrayList<Order> = ArrayList()
    private lateinit var ordersAdapter: OrdersAdapter
    private lateinit var orderListView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_my_requests, container, false)
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
                replace(R.id.container_screens, OrderDetails())
                addToBackStack("Detalhes")
                commit()
            }
        }
    }

    private fun getOrdersFromDB(view: View) {
        val mAuth = FirebaseAuth.getInstance()
        val userId = mAuth.currentUser!!.uid
        val db = Firebase.firestore

        db.collection("orders").whereEqualTo("consumerId", userId)
            .get()
            .addOnSuccessListener { documents ->
                this.orderList.clear()
                for(document in documents){
                    val order = Order(
                        document.data["consumerId"].toString(),
                        document.data["providerId"].toString(),
                        document.data["totalItems"].toString().toInt(),
                        document.data["value"].toString().toFloat(),
                        (document.data["creationDate"] as Timestamp).toDate(),
                        (document.data["collectionDate"] as Timestamp).toDate(),
                        (document.data["deliveryDate"] as Timestamp).toDate(),
                        document.data["status"].toString()
                    )

                    this.orderList.add(order)
                }

                initializeViews(view)

            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Ocorreu um erro ao buscar seus pedidos",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}