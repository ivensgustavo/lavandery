package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.ConsumerPreferencesAdapter
import dadm.quixada.ufc.lavandery.internalModels.ConsumerPreference

class ConsumerPreferencesFragment : Fragment() {

    private var consumerPreferences: ArrayList<ConsumerPreference> = ArrayList()
    private lateinit var consumerPreferencesListView: ListView
    private lateinit var adapter: ConsumerPreferencesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumer_preferences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initializeViews(view)
        getPreferencesFromDB()
    }

    private fun initializeViews(view: View){
        this.consumerPreferencesListView = view.findViewById(R.id.consumer_preferences_list_view)
        this.initializeConsumerPreferencesList()
        this.adapter = ConsumerPreferencesAdapter(requireActivity(), consumerPreferences)

        this.consumerPreferencesListView.adapter = this.adapter
    }

    private fun initializeConsumerPreferencesList(){
        consumerPreferences.add(ConsumerPreference("Roupas passadas", false))
        consumerPreferences.add(ConsumerPreference("Roupas perfumadas", false))
        consumerPreferences.add(ConsumerPreference("Camisetas no cabide", false))
        consumerPreferences.add(ConsumerPreference("Pedidos na portaria", false))
    }

    private fun getPreferencesFromDB(){
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val db = Firebase.firestore

        val docRef = db.collection("users").document(userID)
        docRef.get()
            .addOnSuccessListener {document ->
                val preferences = document.data?.get("preferences") as HashMap<String, Boolean>
                this.consumerPreferences.clear()
                this.consumerPreferences.add(
                    ConsumerPreference("Roupas passadas", preferences["ironed_clothes"].toString().toBoolean())
                )
                this.consumerPreferences.add(
                    ConsumerPreference("Roupas perfumadas", preferences["scented_clothes"].toString().toBoolean())
                )
                this.consumerPreferences.add(
                    ConsumerPreference("Camisetas no cabide", preferences["t_shirts_on_hanger"].toString().toBoolean())
                )
                this.consumerPreferences.add(
                    ConsumerPreference("Pedidos na portaria", preferences["orders_at_reception"].toString().toBoolean())
                )
                this.adapter.notifyDataSetChanged()
            }
    }


}