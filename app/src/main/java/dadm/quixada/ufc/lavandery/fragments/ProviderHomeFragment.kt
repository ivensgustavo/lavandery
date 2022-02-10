package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import dadm.quixada.ufc.lavandery.HomeActivity
import dadm.quixada.ufc.lavandery.ProviderHomeActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.logic.UserService

class ProviderHomeFragment : Fragment() {

    private val userService = UserService()
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_provider_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSolicitacoes: Button = view.findViewById(R.id.btn_requests)
        val btnScheduleds: Button = view.findViewById(R.id.btn_scheduleds)
        val btnWashing: Button = view.findViewById(R.id.btn_washing)
        val btnCarrying: Button = view.findViewById(R.id.btn_carrying)
        val btndelivered: Button = view.findViewById(R.id.btn_delivered)


        btnSolicitacoes.setOnClickListener {
            openOrdersFragment("Novos pedidos", "Enviado")
        }

        btnScheduleds.setOnClickListener {
            openOrdersFragment("Pedidos Agendados", "Agendado")
        }

        btnWashing.setOnClickListener {
            openOrdersFragment("Pedidos Lavando", "Lavando")
        }

        btnCarrying.setOnClickListener {
            openOrdersFragment("Pedidos em Transporte", "Transportando")
        }

        btndelivered.setOnClickListener {
            openOrdersFragment("Pedidos entregues", "Entregue")
        }

        val providerNameTextView: TextView = view.findViewById(R.id.provider_name)

        userService.getUser(mAuth.currentUser!!.uid){ user ->
            if(user != null){
                providerNameTextView.text = user.name
            }
        }
    }

    private fun openOrdersFragment(screenTitle: String, orderStatus: String){

        val bundle = Bundle()
        bundle.putString("screenTitle", screenTitle)
        bundle.putString("orderStatus", orderStatus)
        val providerOrdersFragment = ProviderOrdersFragment()
        providerOrdersFragment.arguments = bundle
        val fragmentManager = (context as ProviderHomeActivity).supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.container_provider_screens, providerOrdersFragment)
            addToBackStack("Novo pedido")
            commit()
        }
    }

}