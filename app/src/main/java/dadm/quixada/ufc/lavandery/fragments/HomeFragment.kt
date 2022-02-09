package dadm.quixada.ufc.lavandery.fragments


import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
        BitmapHelper.vectorToBitmap(requireContext(), R.drawable.ic_pin)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        mapFragment.getMapAsync { googleMap ->
            googleMap.setInfoWindowAdapter(MarkerInfoWindowAdapter(requireActivity()))

            this.fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location !== null) {
                    addMarkers(googleMap)
                    setInitialPosition(googleMap, location)
                }
            }.addOnFailureListener{
                Log.d("Error on map","error getting current location")
            }

        }

        return view
    }

    private fun addMarkers(googleMap: GoogleMap) {
        providerService.getAllProviders { result ->
            if (result != null) {
                for (provider in result) {
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

                    googleMap.setOnInfoWindowClickListener { marker ->
                        val tag = marker.tag

                        if(tag != null){
                            val provider = tag as Provider
                            openNewOrderScreen(provider.id)
                        }
                    }
                }
            }
        }

    }


    private fun setInitialPosition(googleMap: GoogleMap, location: Location){
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(location.latitude, location.longitude))
                .title("Localização atual")
        )
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    location.latitude,
                    location.longitude
                ),
                13.0f
            )
        )
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