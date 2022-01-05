package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.LaundryListAdapter
import dadm.quixada.ufc.lavandery.internalModels.LaundryListItem

class OrderBasketFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val laundryList: ArrayList<LaundryListItem> = ArrayList()

        laundryList.add(LaundryListItem("Camiseta", 7, 3.0.toFloat()))
        laundryList.add(LaundryListItem("Camisa", 2, 5.0.toFloat()))
        laundryList.add(LaundryListItem("Short", 3, 4.0.toFloat()))
        laundryList.add(LaundryListItem("Calças", 7, 3.0.toFloat()))
        laundryList.add(LaundryListItem("Vestido", 2, 5.0.toFloat()))
        laundryList.add(LaundryListItem("Saia", 3, 4.0.toFloat()))
        laundryList.add(LaundryListItem("Casaco", 7, 3.0.toFloat()))


        val laundryListView: ListView = view.findViewById(R.id.order_basket_list_view)
        val adapter = LaundryListAdapter(requireActivity(), laundryList)

        laundryListView.adapter = adapter
    }
}