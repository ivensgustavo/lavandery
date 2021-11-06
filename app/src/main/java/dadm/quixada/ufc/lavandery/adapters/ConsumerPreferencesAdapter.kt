package dadm.quixada.ufc.lavandery.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.internalModels.ConsumerPreference

class ConsumerPreferencesAdapter(private val context: Activity, private val arrayList: ArrayList<ConsumerPreference>):
ArrayAdapter<ConsumerPreference>(context, R.layout.consumer_preferences_list_item ,arrayList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.consumer_preferences_list_item, null)

        val consumerPreference: ConsumerPreference = arrayList[position]
        val preferenceName: TextView = view.findViewById(R.id.consumer_preference_name)

        preferenceName.text = consumerPreference.name

        return view
    }
}