package dadm.quixada.ufc.lavandery.adapters

import android.app.Activity
import android.os.Build
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dadm.quixada.ufc.lavandery.HomeActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.fragments.OrderDetails
import dadm.quixada.ufc.lavandery.internalModels.Order
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class OrdersAdapter(private val context:Activity, private val orderList: ArrayList<Order>):
    ArrayAdapter<Order>(context, R.layout.order_list_item, orderList){

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.order_list_item, null)

        val order:Order = orderList[position]
        val orderDateTextView: TextView = view.findViewById(R.id.order_date)
        val orderStatusTextView: TextView = view.findViewById(R.id.order_status)
        val orderQtyItemsTextView:TextView = view.findViewById(R.id.order_total_items)
        val orderStatusImageView: ImageView = view.findViewById(R.id.order_status_icon)
        val bgIconOrderStatus: LinearLayout = view.findViewById(R.id.bg_icon_order_status)

        orderDateTextView.text = fomatData(order.creationDate)
        orderStatusTextView.text = order.status
        orderQtyItemsTextView.text = order.qtyItens.toString() + " items"
        orderStatusImageView.setImageResource(resolveIcon(order.status))
        bgIconOrderStatus.setBackgroundResource(resolveColor(order.status))

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fomatData(dt: LocalDate): String {
        val formatter = DateTimeFormatter
            .ofPattern("dd MMM YYYY", Locale("pt-br"))

        return  formatter.format(dt)
    }

    private fun resolveIcon(status: String): Int{
        return when(status){
            "Lavando" -> R.drawable.ic_lavando
            "Agendado" -> R.drawable.ic_agendado
            "Transportando" -> R.drawable.ic_transportando
            else -> R.drawable.ic_entregue
        }
    }

    private fun resolveColor(status: String): Int{
        return when(status){
            "Lavando" -> R.drawable.bg_lavando
            "Agendado" -> R.drawable.bg_agendado
            "Transportando" -> R.drawable.bg_transportando
            else -> R.drawable.bg_entregue
        }
    }
}