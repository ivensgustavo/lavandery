package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.LaundryListAdapter
import dadm.quixada.ufc.lavandery.internalModels.LaundryListItem
import dadm.quixada.ufc.lavandery.internalModels.Order


class OrderDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        insertFragment(R.id.container_order_status, OrderStatusFragment())
        insertFragment(R.id.container_order_basket, OrderBasketFragment())
        insertFragment(R.id.container_order_address, OrderAddressFragment())
    }

    private fun insertFragment(container: Int, fragment: Fragment) {
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.add(container, fragment)
        transaction.commit()
    }




}