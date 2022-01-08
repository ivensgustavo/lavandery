package dadm.quixada.ufc.lavandery.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.internalModels.LaundryBasketItem

class LaundryListAdapter(private val context:Activity, private val laundryBasket: ArrayList<LaundryBasketItem>):
    ArrayAdapter<LaundryBasketItem>(context, R.layout.order_list_item, laundryBasket){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.laundry_list_item, null)

        val itemTypeView: TextView = view.findViewById(R.id.type_of_item_in_basket)
        val itemQuantityView: TextView = view.findViewById(R.id.item_quantity_in_basquet)
        val itemValueView: TextView = view.findViewById(R.id.price_by_type)

        val laundryBasketItem: LaundryBasketItem = laundryBasket[position]

        itemTypeView.text = laundryBasketItem.partType
        itemQuantityView.text = laundryBasketItem.quantity.toString()+"x"
        itemValueView.text = "R$ "+String.format("%.2f", laundryBasketItem.pricePerPiece * laundryBasketItem.quantity)

        return view
    }
}