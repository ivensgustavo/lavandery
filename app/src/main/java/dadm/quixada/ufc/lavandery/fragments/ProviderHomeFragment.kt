package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import dadm.quixada.ufc.lavandery.HomeActivity
import dadm.quixada.ufc.lavandery.ProviderHomeActivity
import dadm.quixada.ufc.lavandery.R

class ProviderHomeFragment : Fragment() {

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

        btnSolicitacoes.setOnClickListener {
            openOrdersFragment()
        }
    }

    private fun openOrdersFragment(screenTitle: String){

        val bundle = Bundle()
        bundle.putString("screenTitle", screenTitle)
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