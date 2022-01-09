package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.EditCellPhoneActivity
import dadm.quixada.ufc.lavandery.EditEmailActivity
import dadm.quixada.ufc.lavandery.EditName
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.AccountSettingAdapter
import dadm.quixada.ufc.lavandery.internalModels.AccountSetting


class AccountSettingsFragment : Fragment() {

    private lateinit var accountSettingsList: ArrayList<AccountSetting>
    private lateinit var  accountSettingsAdapter: AccountSettingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)

    }

    private fun initializeViews(view: View){
        accountSettingsList = this.populateAccountSettingsList()
        val accountSettingsListView: ListView = view.findViewById(R.id.account_settings_list_view)
        accountSettingsAdapter = AccountSettingAdapter(requireActivity(), accountSettingsList)
        accountSettingsListView.adapter = accountSettingsAdapter
    }

    private fun populateAccountSettingsList(): ArrayList<AccountSetting> {
        val list: ArrayList<AccountSetting> = ArrayList()

        list.add(AccountSetting("Nome", "Gustavo Ivens"))
        list.add(AccountSetting("Email", "gustavoivens@gmail.com"))
        list.add(AccountSetting("Celular", "+55 88 992436247"))

        return list
    }

}