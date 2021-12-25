package dadm.quixada.ufc.lavandery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import dadm.quixada.ufc.lavandery.adapters.LaundryListAdapter
import dadm.quixada.ufc.lavandery.internalModels.LaundryListItem

class OrderDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        val laundryList: ArrayList<LaundryListItem> = ArrayList()

        laundryList.add(LaundryListItem("Camisetas", 7, 3.0.toFloat()))
        laundryList.add(LaundryListItem("Camisas", 2, 5.0.toFloat()))
        laundryList.add(LaundryListItem("Shorts", 3, 4.0.toFloat()))
        laundryList.add(LaundryListItem("Calças", 7, 3.0.toFloat()))
        laundryList.add(LaundryListItem("Vestidos", 2, 5.0.toFloat()))
        laundryList.add(LaundryListItem("Saias", 3, 4.0.toFloat()))
        laundryList.add(LaundryListItem("Casacos", 7, 3.0.toFloat()))


        val laundryListView: ListView = findViewById(R.id.details_laundry_list_view)
        val adapter = LaundryListAdapter(this, laundryList)

        laundryListView.adapter = adapter

    }
}