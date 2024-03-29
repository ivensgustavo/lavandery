package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.internalModels.LaundryBasketItem


class CountPartsFragment : Fragment() {

    private lateinit var partTypeTextView: TextView
    private lateinit var pricePerPieceTextView: TextView
    private lateinit var btnIncrementItems: Button
    private lateinit var btnDecrementItems: Button
    private lateinit var itemsTextView: TextView
    private var laundryBasketItem: LaundryBasketItem =
        LaundryBasketItem("", 0, 0.0f)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_count_parts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
    }

    private fun initializeViews(view: View) {
        partTypeTextView = view.findViewById(R.id.kind_of_clothes)
        pricePerPieceTextView = view.findViewById(R.id.price_per_piece)
        btnIncrementItems = view.findViewById(R.id.btn_increment_item_qty)
        btnDecrementItems = view.findViewById(R.id.btn_decrement_item_qty)
        itemsTextView = view.findViewById(R.id.items_quantity)

        pricePerPieceTextView.text = laundryBasketItem.pricePerPiece.toString()
        itemsTextView.text = laundryBasketItem.quantity.toString()

        btnIncrementItems.setOnClickListener { incrementItems() }
        btnDecrementItems.setOnClickListener { decrementItems() }
    }

    private fun incrementItems() {

        if (laundryBasketItem.quantity >= 0) {
            laundryBasketItem.quantity++
            itemsTextView.text = laundryBasketItem.quantity.toString()
            val newOrderFragment = parentFragment as NewOrderFragment
            Log.d("total de item do pedido", newOrderFragment.getQtyOrderItems().toString())
            newOrderFragment.setQtyOrderItems(newOrderFragment.getQtyOrderItems() + 1)
            newOrderFragment.setTotalOrderValue(
                newOrderFragment.getTotalOrderValue() + this.laundryBasketItem.pricePerPiece
            )
        }

    }

    private fun decrementItems() {
        if (laundryBasketItem.quantity > 0) {
            laundryBasketItem.quantity--
            itemsTextView.text = laundryBasketItem.quantity.toString()
            val newOrderFragment = parentFragment as NewOrderFragment
            newOrderFragment.setQtyOrderItems(newOrderFragment.getQtyOrderItems() - 1)
            newOrderFragment.setTotalOrderValue(
                newOrderFragment.getTotalOrderValue() - laundryBasketItem.pricePerPiece
            )
        }
    }

    fun setKindOfClothes(kindOfClothes: String) {
        partTypeTextView.text = kindOfClothes
        laundryBasketItem.partType = kindOfClothes
    }

    fun setPricePerPiece(pricePerPiece: Float) {
        this.laundryBasketItem.pricePerPiece = pricePerPiece
        pricePerPieceTextView.text =
            "R$ " + String.format("%.2f", pricePerPiece) + " / item"
    }

    fun getLaundryBasketItem(): LaundryBasketItem {
        return laundryBasketItem
    }

}