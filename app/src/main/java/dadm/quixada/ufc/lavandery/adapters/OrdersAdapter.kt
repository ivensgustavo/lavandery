package dadm.quixada.ufc.lavandery.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import dadm.quixada.ufc.lavandery.OrderDetailsActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.internalModels.Order

class OrdersAdapter(private val context:Activity, private val orderList: ArrayList<Order>):
    ArrayAdapter<Order>(context, R.layout.order_list_item, orderList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.order_list_item, null)

        val order:Order = orderList[position]
        val orderStatusTextView: TextView = view.findViewById(R.id.order_status)
        val orderQtyPartsTextView:TextView = view.findViewById(R.id.order_quantity_of_parts)
        val orderValueTextView:TextView = view.findViewById(R.id.order_value)
        val orderDetailsButton: TextView = view.findViewById(R.id.open_order_details_btn)

        orderStatusTextView.text = order.status
        orderQtyPartsTextView.text = order.qtyParts.toString()
        orderValueTextView.text = order.value.toString()

        orderDetailsButton.setOnClickListener {
            val intent = Intent(context, OrderDetailsActivity::class.java)
            context.startActivity(intent)
        }

        return view
    }
}