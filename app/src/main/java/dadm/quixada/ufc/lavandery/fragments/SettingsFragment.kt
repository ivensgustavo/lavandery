package dadm.quixada.ufc.lavandery.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import dadm.quixada.ufc.lavandery.ConsumerPreferencesActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.SettingsAdapter
import dadm.quixada.ufc.lavandery.internalModels.SettingItem

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settingsList: ArrayList<SettingItem> = ArrayList()

        settingsList.add(SettingItem("Preferências de serviço", R.drawable.ic_services_preferences))
        settingsList.add(SettingItem("Dados da conta", R.drawable.ic_account_settings))
        settingsList.add(SettingItem("Sobre o Lavandery", R.drawable.ic_outline_info))

        val adapter = SettingsAdapter(requireActivity(), settingsList)
        val listView: ListView = view.findViewById(R.id.settings_list_view)
        listView.adapter = adapter

        listView.setOnItemClickListener { adapterView, view, itemPosition, l ->
            when (itemPosition) {
                0 -> openNewSettingGroup(
                    ConsumerPreferencesFragment(),
                    "consumer_preferences_fragment"
                )
                1 -> {
                    requireActivity().supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container_screens, AccountSettingsFragment())
                        addToBackStack("account_settings_fragment")
                        commit()
                    }
                }
                else -> Toast.makeText(context, "Ainda não implementado", Toast.LENGTH_SHORT).show()
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
}