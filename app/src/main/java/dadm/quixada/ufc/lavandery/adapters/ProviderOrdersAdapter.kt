package dadm.quixada.ufc.lavandery.adapters

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import dadm.quixada.ufc.lavandery.HomeActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.fragments.OrderDetailsFragment
import dadm.quixada.ufc.lavandery.logic.OrderService
import dadm.quixada.ufc.lavandery.logic.UserService
import dadm.quixada.ufc.lavandery.models.Order
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProviderOrdersAdapter(private val context: Activity, private val orderList: ArrayList<Order>) :
    ArrayAdapter<Order>(context, R.layout.order_list_item, orderList) {

    private val userService = UserService()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.provider_order_list_item, null)

        val order: Order = orderList[position]
        val consumerNameTextView: TextView = view.findViewById(R.id.consumer_name)
        val totalItemsTextView: TextView = view.findViewById(R.id.total_items)
        val valueTextView: TextView = view.findViewById(R.id.order_value)
        val collectionDateTextView: TextView = view.findViewById(R.id.collection_date)
        val deliveryDateTextView: TextView = view.findViewById(R.id.delivery_date)

        userService.getUser(order.consumerId){ user ->
            if(user != null){
                consumerNameTextView.text = "${user.name} ${user.surname}"
            }
        }

        totalItemsTextView.text = order.totalItems.toString()
        valueTextView.text = order.value.toString()
        collectionDateTextView.text = "${formatData(order.collectionDate)}, ${order.collectionTime}h"
        deliveryDateTextView.text =  "${formatData(order.deliveryDate)}, ${order.deliveryTime}h"

        return view
    }

    private fun formatData(dt: Date): String {
        val format = "dd MMM yyyy"
        val sdf = SimpleDateFormat(format, Locale("pt-br"))

        return sdf.format(dt)
    }
}