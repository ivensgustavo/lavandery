package dadm.quixada.ufc.lavandery.fragments


import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.HomeActivity
import dadm.quixada.ufc.lavandery.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeActivity = requireActivity() as HomeActivity
        homeActivity.showBottomNavigation()

        val btnNewOrder: Button = view.findViewById(R.id.btn_new_order)
        btnNewOrder.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("laundry_provider_id", "hJRIGH85h1TeS9PBE6yi")

            val newOrderFragment = NewOrderFragment()
            newOrderFragment.arguments = bundle

            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(R.id.container_screens, newOrderFragment)
                addToBackStack(null)
                commit()
            }
        }

        val btnOpenMyAddressBottomSheet: Button = view.findViewById(R.id.btn_open_my_address_bottom_sheet)

        btnOpenMyAddressBottomSheet.setOnClickListener {
            val dialog = SelectAddressFragment()
            dialog.show(requireActivity().supportFragmentManager, "my addresses fragment")
        }


        val helloTextView: TextView = view.findViewById(R.id.hello_text)

        val mAuth = FirebaseAuth.getInstance()
        val userId = mAuth.currentUser!!.uid
        val db = Firebase.firestore

        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if(document != null){
                    val name = document.data!!["name"].toString()
                    helloTextView.text = "Olá, $name"
                }
            }
    }
}