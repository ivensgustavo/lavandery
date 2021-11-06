package dadm.quixada.ufc.lavandery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import dadm.quixada.ufc.lavandery.adapters.ConsumerPreferencesAdapter
import dadm.quixada.ufc.lavandery.internalModels.ConsumerPreference

class ConsumerPreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumer_preferences)

        val consumerPreferences: ArrayList<ConsumerPreference> = ArrayList()
        consumerPreferences.add(ConsumerPreference("Roupas passadas", false))
        consumerPreferences.add(ConsumerPreference("Roupas perfumadas", false))
        consumerPreferences.add(ConsumerPreference("Camisetas no cabide", false))
        consumerPreferences.add(ConsumerPreference("Pedidos na portaria", false))

        val consumerPreferencesListView: ListView = findViewById(R.id.consumer_preferences_list_view)
        val adapter: ConsumerPreferencesAdapter = ConsumerPreferencesAdapter(this, consumerPreferences)

        consumerPreferencesListView.adapter = adapter

    }
}