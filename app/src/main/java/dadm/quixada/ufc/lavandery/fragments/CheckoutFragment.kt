package dadm.quixada.ufc.lavandery.fragments


import android.app.DatePickerDialog
import android.os.Build

import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.LaundryListAdapter
import dadm.quixada.ufc.lavandery.internalModels.LaundryBasketItem
import android.view.View.MeasureSpec
import android.widget.*
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class CheckoutFragment : Fragment() {

    private lateinit var basketListView: ListView
    private lateinit var btnSelectCollectionDate: Button
    private lateinit var btnSelectDeliveryDate: Button
    private lateinit var collectionDateTextView: TextView
    private lateinit var deliveryDateTextView: TextView
    private var collectionDate: Date = Date()
    private var deliveryDate: Date = Date()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeBasketListView(view)
        initializeViews(view)
        configureDateSelectButtons()
    }

    private fun initializeViews(view:View){
        this.btnSelectCollectionDate = view.findViewById(R.id.btn_select_collection_date)
        this.btnSelectDeliveryDate = view.findViewById(R.id.btn_select_delivery_date)
        this.collectionDateTextView = view.findViewById(R.id.checkout_collection_date)
        this.deliveryDateTextView = view.findViewById(R.id.checkout_delivery_date)
    }

    private fun initializeBasketListView(view: View) {
        val laundryBasket = arguments?.get("laundry_basket") as ArrayList<LaundryBasketItem>
        val adapter = LaundryListAdapter(requireActivity(), laundryBasket)

        this.basketListView = view.findViewById(R.id.checkout_basket_list_view)
        this.basketListView.adapter = adapter

        this.setListViewHeight()

    }

    private fun configureDateSelectButtons(){
        val myCalendar: Calendar = Calendar.getInstance()

        val datePickerCollectionDate =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONDAY, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                changeCollectionDate(myCalendar)

            }

        val datePickerDeliveryDate =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONDAY, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                changeDeliveryDate(myCalendar)

            }

        this.btnSelectCollectionDate.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                datePickerCollectionDate,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        this.btnSelectDeliveryDate.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                datePickerDeliveryDate,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun formatDate(dt: Date): String {
        val format = "dd MMM yyyy"
        val sdf = SimpleDateFormat(format, Locale("pt-br"))

        return sdf.format(dt)
    }

    private fun changeCollectionDate(calendar: Calendar) {
        this.collectionDate = calendar.time
        this.collectionDateTextView.text = formatDate(calendar.time)
    }

    private fun changeDeliveryDate(calendar: Calendar) {
        this.deliveryDate = calendar.time
        this.deliveryDateTextView.text = formatDate(calendar.time)
    }

    private fun setListViewHeight() {

        val adapter = this.basketListView.adapter

        val desiredWidth =
            MeasureSpec.makeMeasureSpec(this.basketListView.width, MeasureSpec.UNSPECIFIED)
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