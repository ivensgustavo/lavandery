package dadm.quixada.ufc.lavandery.fragments


import android.app.DatePickerDialog


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.LaundryListAdapter
import dadm.quixada.ufc.lavandery.internalModels.LaundryBasketItem
import android.view.View.MeasureSpec
import android.widget.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dadm.quixada.ufc.lavandery.HomeActivity
import dadm.quixada.ufc.lavandery.helpers.AvaiableTimesHelper
import dadm.quixada.ufc.lavandery.logic.AddressService
import dadm.quixada.ufc.lavandery.logic.OrderService
import dadm.quixada.ufc.lavandery.models.Address
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CheckoutFragment : Fragment() {

    private lateinit var basketListView: ListView
    private lateinit var btnSelectCollectionDate: Button
    private lateinit var btnSelectDeliveryDate: Button
    private lateinit var collectionDateTextView: TextView
    private lateinit var deliveryDateTextView: TextView
    private lateinit var collectionTimeRadioGroup: RadioGroup
    private lateinit var deliveryTimeRadioGroup: RadioGroup
    private lateinit var btnConfirmAndSchedule: Button
    private lateinit var totalItemsTextView: TextView
    private lateinit var laundryBasketTotalTextView: TextView
    private lateinit var orderTotalTextView: TextView
    private var collectionDate: Date = Date()
    private var deliveryDate: Date = Date()
    private var laundryBasket: ArrayList<LaundryBasketItem> = ArrayList()
    private var laundryBasketTotalValue: Float = 0.0f
    private var orderTotalValue: Float = 0.0f
    private lateinit var currentAddress: Address

    private lateinit var localTextView: TextView
    private lateinit var complementTextView: TextView
    private lateinit var cepTextView: TextView

    private val orderService = OrderService()
    private val addressService = AddressService()
    private lateinit var myView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_checkout, container, false)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.laundryBasket = arguments?.get("laundry_basket") as ArrayList<LaundryBasketItem>
        this.laundryBasketTotalValue = arguments?.get("laundry_basket_value") as Float
        this.orderTotalValue = this.laundryBasketTotalValue + 10

        initializeBasketListView(view)
        initializeViews(view)
        initializeAddress()
        configureDateSelectButtons()
    }

    private fun initializeViews(view: View) {
        this.btnSelectCollectionDate = view.findViewById(R.id.btn_select_collection_date)
        this.btnSelectDeliveryDate = view.findViewById(R.id.btn_select_delivery_date)
        this.collectionDateTextView = view.findViewById(R.id.checkout_collection_date)
        this.deliveryDateTextView = view.findViewById(R.id.checkout_delivery_date)
        this.collectionTimeRadioGroup = view.findViewById(R.id.radio_group_collection_time)
        this.deliveryTimeRadioGroup = view.findViewById(R.id.radio_group_delivery_time)
        this.btnConfirmAndSchedule = view.findViewById(R.id.btn_confirm_and_schedule)
        this.totalItemsTextView = view.findViewById(R.id.checkout_total_basket_items)
        this.laundryBasketTotalTextView = view.findViewById(R.id.checkout_total_basket_value)
        this.orderTotalTextView = view.findViewById(R.id.checkout_total_order_value)

        this.localTextView = view.findViewById(R.id.checkout_address_local)
        this.cepTextView = view.findViewById(R.id.checkout_address_cep)
        this.complementTextView = view.findViewById(R.id.checkout_address_complement)

        this.totalItemsTextView.text = this.laundryBasket.size.toString()+ " items"
        this.laundryBasketTotalTextView.text = "R$ " + String.format("%.2f", this.laundryBasketTotalValue)
        this.orderTotalTextView.text = "R$ " + String.format("%.2f", this.orderTotalValue)

        this.btnConfirmAndSchedule.setOnClickListener {
            sendOrder(view)
        }
    }

    private fun getCollectionTimeSelected(view: View): String {
        return view.findViewById<RadioButton>(this.collectionTimeRadioGroup.checkedRadioButtonId)
            .text.toString()
    }

    private fun getDeliveryTimeSelected(view: View): String {
        return view.findViewById<RadioButton>(this.deliveryTimeRadioGroup.checkedRadioButtonId)
            .text.toString()
    }

    private fun sendOrder(view: View) {
        val providerId = arguments?.get("laundry_provider_id") as String
        val totalItems = this.laundryBasket.size
        val collectionTimeSelected = getCollectionTimeSelected(view)
        val deliveryTimeSelected = getDeliveryTimeSelected(view)


        orderService.createOrder(
            providerId,
            totalItems,
            this.laundryBasket,
            this.orderTotalValue,
            Date(),
            this.collectionDate,
            this.deliveryDate,
            "Enviado",
            collectionTimeSelected,
            deliveryTimeSelected,
            this.currentAddress.id
        ){
            result ->
            if(result){
                showConfirmationMessage()
            }else{
               showErrorMessage()
            }
        }


    }

    private fun showConfirmationMessage() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Pedido enviado")
            .setMessage(
                "Seu pedido foi enviado para a pessoa que você escolheu para lavar suas roupas. " +
                        "Fique atento a resposta a seu pedido"
            )
            .setPositiveButton("Ok") { _, _ ->
                navigateToHome()
            }
            .show()
    }

    private fun showErrorMessage(){
        Toast.makeText(
            requireContext(),
            "Ocorreu um erro ao enviar seu pedido. Tente novamente",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun navigateToHome() {
        val activity = requireActivity() as HomeActivity
        activity.cleanBackStack()

        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_screens, HomeFragment())
            addToBackStack("home_fragment")
            commit()
        }
    }

    private fun initializeBasketListView(view: View) {
        val laundryBasket = arguments?.get("laundry_basket") as ArrayList<LaundryBasketItem>
        val adapter = LaundryListAdapter(requireActivity(), laundryBasket)

        this.basketListView = view.findViewById(R.id.checkout_basket_list_view)
        this.basketListView.adapter = adapter

        this.setListViewHeight()
    }

    private fun initializeAddress(){
        addressService.getCurrentAddress { result ->
            if(result != null){
                this.localTextView.text = result.street +", " + result.number.toString()
                this.cepTextView.text = result.cep.toString()
                this.complementTextView.text = result.complement
                this.currentAddress = result
            }
        }
    }

    private fun configureDateSelectButtons() {
        val myCalendar: Calendar = Calendar.getInstance()

        val datePickerCollectionDate =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONDAY, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                changeCollectionDate(myCalendar)

            }

        val datePickerDeliveryDate =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
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
        this.setAvailableTimesForCollection()
    }

    private fun changeDeliveryDate(calendar: Calendar) {
        this.deliveryDate = calendar.time
        this.deliveryDateTextView.text = formatDate(calendar.time)
        setAvailableTimesForDelivery()
    }

    private fun setAvailableTimesForCollection(){
        val providerId = arguments?.get("laundry_provider_id") as String

        val allTimes = AvaiableTimesHelper.getAllCollectionTimesId()

        for( item in allTimes){
            val rb: RadioButton = myView.findViewById(item.value)
            rb.visibility = View.VISIBLE
        }

        orderService.getUnavailableTimes(providerId, this.collectionDate){ result ->  
            for( time in result){
                val rb: RadioButton = myView.findViewById(allTimes[time]!!)
                rb.visibility = View.GONE
            }
        }
    }

    private fun setAvailableTimesForDelivery(){
        val providerId = arguments?.get("laundry_provider_id") as String

        val allTimes = AvaiableTimesHelper.getAllDeliveryTimesId()

        for( item in allTimes){
            val rb: RadioButton = myView.findViewById(item.value)
            rb.visibility = View.VISIBLE
        }

        orderService.getUnavailableTimes(providerId, this.collectionDate){ result ->
            for( time in result){
                val rb: RadioButton = myView.findViewById(allTimes[time]!!)
                rb.visibility = View.GONE
            }
        }
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