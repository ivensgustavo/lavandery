package dadm.quixada.ufc.lavandery.fragments


import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        val btnNewOrder: Button = view.findViewById(R.id.btn_new_order)
        btnNewOrder.setOnClickListener {

            val bundle: Bundle = Bundle()
            bundle.putString("laundry_provider_id", "hJRIGH85h1TeS9PBE6yi")

            val newOrderFragment: NewOrderFragment = NewOrderFragment()
            newOrderFragment.arguments = bundle

            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(R.id.container_screens, newOrderFragment)
                addToBackStack(null)
                commit()
            }
        }
    }
}