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


class OrderStatusFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_status, container, false)
    }

}