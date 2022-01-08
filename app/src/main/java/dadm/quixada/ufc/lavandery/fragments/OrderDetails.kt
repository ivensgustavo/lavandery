package dadm.quixada.ufc.lavandery.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.internalModels.LaundryBasketItem
import dadm.quixada.ufc.lavandery.internalModels.Order
import dadm.quixada.ufc.lavandery.models.Address
import java.time.LocalDate


class OrderDetails : Fragment() {

    private lateinit var order: Order

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_details, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        order = Order(
            "SGG47HGDG6",
            "GH7N78DH4G",
            30,
            70.0.toFloat(),
            LocalDate.parse("2021-12-08"),
            LocalDate.parse("2021-12-09"),
            LocalDate.parse("2021-12-10"),
            "Agendado")

        this.changeStatus()
        this.changeBasket()
        this.changeAddress()
    }

    private fun changeStatus(){
        val orderStatusFragment =
            childFragmentManager.findFragmentById(R.id.order_status_fragment) as OrderStatusFragment

        orderStatusFragment.resolveStatus(order.status)
    }

    private fun changeBasket(){
        val orderbasketFragment =
            childFragmentManager.findFragmentById(R.id.order_basket_fragment) as OrderBasketFragment

        val laundryBasket: ArrayList<LaundryBasketItem> = ArrayList()

        laundryBasket.add(LaundryBasketItem("Camiseta", 7, 3.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Camisa", 2, 5.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Short", 3, 4.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Calças", 7, 3.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Vestido", 2, 5.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Saia", 3, 4.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Casaco", 7, 3.0.toFloat()))

        orderbasketFragment.setOrderBasket(laundryBasket)
        orderbasketFragment.setOrderTotal(170.0f)
    }

    private fun changeAddress(){
        val orderAddressFragment =
            childFragmentManager.findFragmentById(R.id.order_address_fragment) as OrderAddressFragment

        orderAddressFragment.setAddress(Address(
            "Rua Ministro Antonio Coelho",
            619,
            623700,
            "Em frente a Escola do Dep. Júlio Filizola"
        ))
    }




}