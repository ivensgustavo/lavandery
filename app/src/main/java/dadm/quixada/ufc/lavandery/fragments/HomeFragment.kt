package dadm.quixada.ufc.lavandery.fragments


import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import dadm.quixada.ufc.lavandery.HomeActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.MarkerInfoWindowAdapter
import dadm.quixada.ufc.lavandery.logic.ProviderService
import dadm.quixada.ufc.lavandery.logic.UserService
import dadm.quixada.ufc.lavandery.models.User
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomeFragment : Fragment() {

    private val userService = UserService()
    private val providerService = ProviderService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val mapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction().apply {
            replace(R.id.container_map, mapFragment)
            addToBackStack(null)
            commit()
        }

        mapFragment.getMapAsync { googleMap ->

            googleMap.setInfoWindowAdapter(MarkerInfoWindowAdapter(requireActivity()))

            var user = User(
                "89EYTRqKeHhzRpZKT2bXlSucDcB3",
                "Gustavo Ivens",
                "Oliveira Silva",
                "gustavo_ivens@gmail.com",
                "88 99243-6247",
                "Provedor de serviço",
                HashMap<String, Boolean>(),
                ""
            )

            userService.getUser("89EYTRqKeHhzRpZKT2bXlSucDcB3") { result ->
                if (result != null) {
                    user = result
                }
            }

            val saoBenedito = LatLng(-4.0371272, -40.9086704)
            googleMap.clear()
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .position(saoBenedito)
                    .title("Meu Marcador")
            )

            if (marker != null) {
                marker.tag = user
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(saoBenedito, 13.0f))
        }

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeActivity = requireActivity() as HomeActivity
        homeActivity.showBottomNavigation()

        /*val btnNewOrder: Button = view.findViewById(R.id.btn_new_order)
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
        }*/

        val btnOpenMyAddressBottomSheet: Button =
            view.findViewById(R.id.btn_open_my_address_bottom_sheet)

        btnOpenMyAddressBottomSheet.setOnClickListener {
            val dialog = SelectAddressFragment()
            dialog.show(requireActivity().supportFragmentManager, "my addresses fragment")
        }


        val helloTextView: TextView = view.findViewById(R.id.hello_text)

        val mAuth = FirebaseAuth.getInstance()
        val userId = mAuth.currentUser!!.uid

        userService.getUser(userId) { result ->
            if (result != null) {
                helloTextView.text = "Olá, ${result.name}"
            }
        }
    }
}