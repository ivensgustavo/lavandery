package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import androidx.core.content.ContextCompat
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.LaundryListAdapter
import dadm.quixada.ufc.lavandery.internalModels.LaundryListItem
import dadm.quixada.ufc.lavandery.internalModels.Order


class OrderStatusFragment : Fragment() {

    private lateinit var bgStatusScheduled: LinearLayout
    private lateinit var bgStatusPickedUp: LinearLayout
    private lateinit var bgStatusWashing: LinearLayout
    private lateinit var bgStatusDelivered: LinearLayout
    private lateinit var iconStatusScheduled: ImageView
    private lateinit var iconStatusPickedUp: ImageView
    private lateinit var iconStatusWashing: ImageView
    private lateinit var iconStatusDelivered: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      initializeViews(view)

    }

    private fun initializeViews(view: View){
        bgStatusScheduled = view.findViewById(R.id.order_details_status_scheduled)
        bgStatusPickedUp = view.findViewById(R.id.order_details_status_transporting)
        bgStatusWashing= view.findViewById(R.id.order_details_status_washing)
        bgStatusDelivered = view.findViewById(R.id.order_details_status_delivered)
        iconStatusScheduled = view.findViewById(R.id.order_details_img_status_scheduled)
        iconStatusPickedUp = view.findViewById(R.id.order_details_img_status_transporting)
        iconStatusWashing = view.findViewById(R.id.order_details_img_status_washing)
        iconStatusDelivered = view.findViewById(R.id.order_details_img_status_delivered)
    }

    fun resolveStatus(status: String){
        when(status){
            "Agendado" -> changeStatusToScheduled()
            "Peças coletadas" -> changeStatusToPickedUp()
            "Lavando" -> changeStatusToWashing()
            else -> changeStatusToDelivered()
        }
    }

    private fun changeStatusToScheduled(){
        bgStatusScheduled.setBackgroundResource(R.drawable.bg_agendado)
        iconStatusScheduled.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                R.color.main_blue
            ),
            android.graphics.PorterDuff.Mode.SRC_IN
        );
    }

    private fun changeStatusToPickedUp(){
        bgStatusPickedUp.setBackgroundResource(R.drawable.bg_transportando)
        iconStatusPickedUp.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                R.color.main_blue
            ),
            android.graphics.PorterDuff.Mode.SRC_IN
        );
    }

    private fun changeStatusToWashing(){
        bgStatusWashing.setBackgroundResource(R.drawable.bg_lavando)
        iconStatusWashing.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                R.color.main_blue
            ),
            android.graphics.PorterDuff.Mode.SRC_IN
        );
    }

    private fun changeStatusToDelivered(){
        bgStatusDelivered.setBackgroundResource(R.drawable.bg_entregue)
        iconStatusDelivered.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                R.color.main_blue
            ),
            android.graphics.PorterDuff.Mode.SRC_IN
        );
    }

}