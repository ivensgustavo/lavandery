package dadm.quixada.ufc.lavandery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import dadm.quixada.ufc.lavandery.adapters.LaundryListAdapter
import dadm.quixada.ufc.lavandery.internalModels.LaundryBasketItem

class OrderDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        val laundryBasket: ArrayList<LaundryBasketItem> = ArrayList()

        laundryBasket.add(LaundryBasketItem("Camisetas", 7, 3.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Camisas", 2, 5.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Shorts", 3, 4.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Calças", 7, 3.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Vestidos", 2, 5.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Saias", 3, 4.0.toFloat()))
        laundryBasket.add(LaundryBasketItem("Casacos", 7, 3.0.toFloat()))


        val laundryListView: ListView = findViewById(R.id.details_laundry_list_view)
        val adapter = LaundryListAdapter(this, laundryBasket)

        laundryListView.adapter = adapter

    }
}