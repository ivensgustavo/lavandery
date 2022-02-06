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
import dadm.quixada.ufc.lavandery.internalModels.LaundryBasketItem

class OrderBasketFragment : Fragment() {

    private lateinit var orderBasketListView: ListView
    private lateinit var orderBasket: ArrayList<LaundryBasketItem>
    private lateinit var totalValueTextView: TextView
    private lateinit var deliveryValueTextView: TextView
    private lateinit var itemsValueTextView: TextView
    private var orderTotalValue: Float = 0.0f
    private var orderItemsValue: Float = 0.0f
    private var orderDeliveryValue: Float = 0.0f

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
        this.orderBasketListView = view.findViewById(R.id.order_basket_list_view)
        this.totalValueTextView = view.findViewById(R.id.order_basket_total_order_value)
        this.deliveryValueTextView = view.findViewById(R.id.order_basket_total_delivery)
        this.itemsValueTextView = view.findViewById(R.id.order_basket_items_value)
    }

    private fun updateOrderValues(){
        this.totalValueTextView.text = "R$ " + this.orderTotalValue.toString()
        this.itemsValueTextView.text = "R$ " + this.orderItemsValue.toString()
        this.deliveryValueTextView.text = "R$ " + this.orderDeliveryValue.toString()
    }

   fun setOrderBasket(orderBasket: ArrayList<LaundryBasketItem>){
       this.orderBasket = orderBasket

       this.orderBasket.forEach { item ->
           this.orderItemsValue+= item.pricePerPiece * item.quantity
       }

       this.orderTotalValue = this.orderItemsValue + this.orderDeliveryValue

       this.updateListWithItems()
       this.updateOrderValues()
   }

    private fun updateListWithItems(){
        val adapter = LaundryListAdapter(requireActivity(), orderBasket)
        orderBasketListView.adapter = adapter
        adapter.notifyDataSetChanged()
        setListViewHeight()
    }

    fun setOrderDeliveryValue(value: Float){
        this.orderDeliveryValue = value
    }

    private fun setListViewHeight() {

        val adapter = this.orderBasketListView.adapter

        val desiredWidth =
            View.MeasureSpec.makeMeasureSpec(this.orderBasketListView.width, View.MeasureSpec.UNSPECIFIED)
        var totalHeight = 0
        var view: View? = null

        for (i in 0 until adapter.count) {
            view = adapter.getView(i, view, this.orderBasketListView)
            if (i == 0) view.layoutParams = ViewGroup.LayoutParams(
                desiredWidth,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += view.measuredHeight
        }

        val params = this.orderBasketListView.layoutParams
        params.height = totalHeight + this.orderBasketListView.dividerHeight * adapter.count - 3

        this.orderBasketListView.layoutParams = params
        this.orderBasketListView.requestLayout()
    }
}