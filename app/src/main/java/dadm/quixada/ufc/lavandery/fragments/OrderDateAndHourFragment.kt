package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dadm.quixada.ufc.lavandery.R
import java.text.SimpleDateFormat
import java.util.*


class OrderDateAndHourFragment : Fragment() {

    private lateinit var collectionDateTextView: TextView
    private lateinit var collectionHourTextView: TextView
    private lateinit var deliveryDateTextView: TextView
    private lateinit var deliveryHourTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_date_and_hour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
    }

    fun initializeViews(view: View){
        this.collectionDateTextView = view.findViewById(R.id.order_details_collection_date)
        this.collectionHourTextView = view.findViewById(R.id.order_details_collection_hour)
        this.deliveryDateTextView = view.findViewById(R.id.order_details_delivery_date)
        this.deliveryHourTextView = view.findViewById(R.id.order_details_delivery_hour)
    }

    fun setData(
        collectionDate: Date,
        collectionHour: String,
        deliveryDate: Date,
        deliveryHour: String
    ) {
        this.collectionDateTextView.text = formatData(collectionDate)
        this.collectionHourTextView.text = collectionHour
        this.deliveryDateTextView.text = formatData(deliveryDate)
        this.deliveryHourTextView.text = deliveryHour
    }

    private fun formatData(dt: Date): String {
        val format = "dd MMM yyyy"
        val sdf = SimpleDateFormat(format, Locale("pt-br"))

        return sdf.format(dt)
    }


}