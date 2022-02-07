package dadm.quixada.ufc.lavandery.fragments


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import dadm.quixada.ufc.lavandery.HomeActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.MarkerInfoWindowAdapter
import dadm.quixada.ufc.lavandery.helpers.BitmapHelper
import dadm.quixada.ufc.lavandery.logic.AddressService
import dadm.quixada.ufc.lavandery.logic.ProviderService
import dadm.quixada.ufc.lavandery.logic.UserService

import dadm.quixada.ufc.lavandery.models.Provider

import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private val userService = UserService()
    private val providerService = ProviderService()
    private val addressService = AddressService()
    private val laundryIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(requireContext(), R.color.main_blue)
        BitmapHelper.vectorToBitmap(requireContext(), R.drawable.ic_local_laundry, color)
    }

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

            providerService.getAllProviders { result ->
                if (result != null) {
                    addMarkers(googleMap, result)
                }
            }

            setInitialPosition(googleMap)

            googleMap.setOnInfoWindowClickListener { marker ->
                val tag = marker.tag

                if(tag != null){
                    val provider = tag as Provider
                    openNewOrderScreen(provider.id)
                }
            }
        }

        return view
    }

    private fun addMarkers(googleMap: GoogleMap, providers: ArrayList<Provider>) {
        for (provider in providers) {
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .position(
                        LatLng(
                            provider.address!!.latitude,
                            provider.address!!.longitude
                        )
                    )
                    .title(provider.name)
                    .icon(laundryIcon)
                    .flat(true)
            )

            if (marker != null) {
                marker.tag = provider
            }
        }
    }


    private fun setInitialPosition(googleMap: GoogleMap){
        addressService.getCurrentAddress { result ->
            if(result != null){
                val initialPoint = LatLng(result.latitude, result.longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPoint, 12.0f))
            }
        }
    }

    private fun openNewOrderScreen(providerId: String) {
        val bundle = Bundle()
        bundle.putString("laundry_provider_id", providerId)

        val newOrderFragment = NewOrderFragment()
        newOrderFragment.arguments = bundle

        val fragmentManager = (context as HomeActivity).supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.container_screens, newOrderFragment)
            addToBackStack("Novo pedido")
            commit()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeActivity = requireActivity() as HomeActivity
        homeActivity.showBottomNavigation()

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