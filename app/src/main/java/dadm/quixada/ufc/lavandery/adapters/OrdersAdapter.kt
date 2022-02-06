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
import dadm.quixada.ufc.lavandery.models.Order
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrdersAdapter(private val context: Activity, private val orderList: ArrayList<Order>) :
    ArrayAdapter<Order>(context, R.layout.order_list_item, orderList) {

    val orderService = OrderService()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.order_list_item, null)

        val order: Order = orderList[position]
        val orderDateTextView: TextView = view.findViewById(R.id.order_date)
        val orderStatusTextView: TextView = view.findViewById(R.id.order_status)
        val orderQtyItemsTextView: TextView = view.findViewById(R.id.order_total_items)
        val orderStatusImageView: ImageView = view.findViewById(R.id.order_status_icon)
        val bgIconOrderStatus: LinearLayout = view.findViewById(R.id.bg_icon_order_status)

        orderDateTextView.text = formatData(order.creationDate)
        orderStatusTextView.text = order.status
        orderQtyItemsTextView.text = order.totalItems.toString() + " items"
        orderStatusImageView.setImageResource(resolveIcon(order.status))
        bgIconOrderStatus.setBackgroundResource(resolveColor(order.status))

        view.setOnClickListener {
            openOrderDetails(order.id)
        }

        return view
    }

    private fun openOrderDetails(orderId: String) {

        Log.d("Id que veio: ", orderId)

        orderService.getOrder(orderId) { result ->
            if (result != null) {
                val orderDetailsFragment = OrderDetailsFragment()
                orderDetailsFragment.setOrder(result)
                val activity = context as HomeActivity

                activity.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.container_screens, orderDetailsFragment)
                    addToBackStack("edit_cell_phone_fragment")
                    commit()
                }
            }
        }
    }

    private fun formatData(dt: Date): String {
        val format = "dd MMM yyyy"
        val sdf = SimpleDateFormat(format, Locale("pt-br"))

        return sdf.format(dt)
    }

    private fun resolveIcon(status: String): Int {
        return when (status) {
            "Lavando" -> R.drawable.ic_lavando
            "Agendado" -> R.drawable.ic_agendado
            "Transportando" -> R.drawable.ic_transportando
            else -> R.drawable.ic_entregue
        }
    }

    private fun resolveColor(status: String): Int {
        return when (status) {
            "Lavando" -> R.drawable.bg_lavando
            "Agendado" -> R.drawable.bg_agendado
            "Transportando" -> R.drawable.bg_transportando
            else -> R.drawable.bg_entregue
        }
    }
}