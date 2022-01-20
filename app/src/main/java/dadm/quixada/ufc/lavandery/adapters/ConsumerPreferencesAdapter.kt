package dadm.quixada.ufc.lavandery.adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.internalModels.ConsumerPreference

class ConsumerPreferencesAdapter(
    private val context: Activity,
    private val arrayList: ArrayList<ConsumerPreference>
) :
    ArrayAdapter<ConsumerPreference>(context, R.layout.consumer_preferences_list_item, arrayList) {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db = Firebase.firestore

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.consumer_preferences_list_item, null)

        val consumerPreference: ConsumerPreference = arrayList[position]
        val preferenceName: TextView = view.findViewById(R.id.consumer_preference_name)
        val preferenceSwitch: SwitchMaterial = view.findViewById(R.id.switch_preference)
        preferenceSwitch.isChecked = consumerPreference.state

        preferenceName.text = consumerPreference.name

        preferenceSwitch.setOnCheckedChangeListener { _, _ ->
            val userID = mAuth.currentUser!!.uid


            db.collection("users").document(userID)
                .update(
                    "preferences."+getPreferenceCodeInDB(consumerPreference.name),
                    preferenceSwitch.isChecked
                ).addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Ocorreu um erro ao salvar suas preferências. Tente novamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }

        return view
    }

    private fun getPreferenceCodeInDB(preferenceName: String): String {

        var res = ""
        when (preferenceName) {
            "Roupas passadas" -> res = "ironed_clothes"
            "Roupas perfumadas" -> res = "scented_clothes"
            "Camisetas no cabide" -> res = "t_shirts_on_hanger"
            "Pedidos na portaria" -> res = "orders_at_reception"
        }

        return res
    }

}