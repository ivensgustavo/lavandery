package dadm.quixada.ufc.lavandery.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.internalModels.LaundryListItem
import dadm.quixada.ufc.lavandery.internalModels.Order

class LaundryListAdapter(private val context:Activity, private val laundryList: ArrayList<LaundryListItem>):
    ArrayAdapter<LaundryListItem>(context, R.layout.order_list_item, laundryList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.laundry_list_item, null)

        val itemTypeView: TextView = view.findViewById(R.id.laundry_list_item_type)
        val itemQuantityView: TextView = view.findViewById(R.id.laundry_list_item_quantity)
        val itemValueView: TextView = view.findViewById(R.id.laundry_list_item_value)
        val itemTotalView: TextView = view.findViewById(R.id.laundry_list_item_total)

        val laundryListItem: LaundryListItem = laundryList[position]

        itemTypeView.text = laundryListItem.itemType
        itemQuantityView.text = laundryListItem.quantity.toString()
        itemValueView.text = laundryListItem.unitaryValue.toString()
        itemTotalView.text = (laundryListItem.unitaryValue * laundryListItem.quantity).toString()

        return view
    }
}