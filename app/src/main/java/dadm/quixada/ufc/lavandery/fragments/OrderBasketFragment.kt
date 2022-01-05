package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.LaundryListAdapter
import dadm.quixada.ufc.lavandery.internalModels.LaundryListItem
import kotlin.math.round
import kotlin.math.roundToLong

class OrderBasketFragment : Fragment() {

    private lateinit var orderBasketListView: ListView
    private var orderTotalValue: Float = 0.0f
    private lateinit var orderBasket: ArrayList<LaundryListItem>
    private lateinit var orderTotalTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
    }

    private fun initializeViews(view: View){
        orderBasketListView = view.findViewById(R.id.order_basket_list_view)
        orderTotalTextView = view.findViewById(R.id.order_basket_total)
    }

   fun setOrderBasket(orderBasket: ArrayList<LaundryListItem>){
       this.orderBasket = orderBasket
       this.updateListWithItems()
   }

    private fun updateListWithItems(){
        val adapter = LaundryListAdapter(requireActivity(), orderBasket)
        orderBasketListView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun setOrderTotal(total: Float) {
        this.orderTotalValue = total
        this.updateTotalTextView()
    }

    private fun updateTotalTextView() {
        orderTotalTextView.text = "R$ " + String.format("%.2f", orderTotalValue)
    }
}