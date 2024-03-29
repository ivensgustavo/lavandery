package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.SettingsAdapter
import dadm.quixada.ufc.lavandery.internalModels.SettingItem
import dadm.quixada.ufc.lavandery.logic.UserService

class SettingsFragment : Fragment() {

    private val userService = UserService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUserName(view)

        val settingsList: ArrayList<SettingItem> = ArrayList()

        val accountType = arguments?.get("accountType") as String

        settingsList.add(SettingItem("Dados da conta", R.drawable.ic_account_settings))

        if(accountType == "Consumidor"){
            settingsList.add(SettingItem("Preferências de serviço", R.drawable.ic_services_preferences))
        }
        

        val adapter = SettingsAdapter(requireActivity(), settingsList)
        val listView: ListView = view.findViewById(R.id.settings_list_view)
        listView.adapter = adapter

        listView.setOnItemClickListener { adapterView, view, itemPosition, l ->
            when (itemPosition) {
                0 -> openNewSettingGroup(
                    AccountSettingsFragment(),
                    "account_settings_fragment"
                )
                1 -> openNewSettingGroup(
                    ConsumerPreferencesFragment(),
                    "consumer_preferences_fragment"
                )
            }
        }
    }

    private fun openNewSettingGroup(fragment: Fragment, fragmentName: String) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_screens, fragment)
            addToBackStack(fragmentName)
            commit()
        }
    }

    private fun initializeUserName(view: View){

        val helloUserTextView: TextView = view.findViewById(R.id.user_name_in_settings_screen)

        val mAuth = FirebaseAuth.getInstance()
        val userId = mAuth.currentUser!!.uid

        userService.getUser(userId){ result ->
            if(result != null){
                helloUserTextView.text = result.name
            }
        }
    }
}