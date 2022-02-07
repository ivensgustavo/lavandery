package dadm.quixada.ufc.lavandery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.models.User


class MarkerInfoWindowAdapter(
    private val context: Context
) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View? {
       val user = marker?.tag as? User?: return null

        val view = LayoutInflater.from(context).inflate(R.layout.marker_info_contents, null)

        val providerNameTextView: TextView = view.findViewById(R.id.provider_name)
        val numberOfOrderTextView: TextView = view.findViewById(R.id.number_of_orders)

        providerNameTextView.text = user.name
        numberOfOrderTextView.text = "10"

        return view;
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }


}