package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.LaundryListAdapter
import dadm.quixada.ufc.lavandery.internalModels.LaundryBasketItem
import android.view.View.MeasureSpec
import android.widget.ListAdapter


class CheckoutFragment : Fragment() {

    private lateinit var basketListView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeBasketListView(view)
    }

    private fun initializeBasketListView(view:View){
        val laundryBasket = arguments?.get("laundry_basket") as ArrayList<LaundryBasketItem>
        val adapter = LaundryListAdapter(requireActivity(), laundryBasket)

        this.basketListView = view.findViewById(R.id.checkout_basket_list_view)
        this.basketListView.adapter = adapter

        this.setListViewHeight()

    }

    private fun setListViewHeight(){

        val adapter = this.basketListView.adapter

        val desiredWidth = MeasureSpec.makeMeasureSpec(this.basketListView.width, MeasureSpec.UNSPECIFIED)
        var totalHeight = 0
        var view: View? = null

        for (i in 0 until adapter.count) {
            view = adapter.getView(i, view, this.basketListView)
            if (i == 0) view.layoutParams = ViewGroup.LayoutParams(
                desiredWidth,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED)
            totalHeight += view.measuredHeight
        }

        val params = this.basketListView.layoutParams
        params.height = totalHeight + this.basketListView.dividerHeight * adapter.count - 3

        this.basketListView.layoutParams = params
        this.basketListView.requestLayout()
    }


}