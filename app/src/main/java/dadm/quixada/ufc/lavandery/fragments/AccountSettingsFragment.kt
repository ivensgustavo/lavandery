package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.AccountSettingAdapter
import dadm.quixada.ufc.lavandery.internalModels.AccountSetting


class AccountSettingsFragment : Fragment() {

    private lateinit var accountSettingsList: ArrayList<AccountSetting>
    private lateinit var exitButton: TextView
    private lateinit var mAuth: FirebaseAuth
    private var userName: String = ""
    private var userSurname: String = ""
    private var userEmail: String = ""
    private var userCellPhone: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_account_settings, container, false)

        mAuth = FirebaseAuth.getInstance()
        getAccountDataFromDB(view)

        return view
    }

    private fun getAccountDataFromDB(view: View) {
        val currentUserId = mAuth.currentUser!!.uid
        val db = Firebase.firestore

        val documentRef = db.collection("users").document(currentUserId)
        documentRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    this.userName = document.data!!["name"].toString()
                    this.userSurname = document.data!!["surname"].toString()
                    this.userCellPhone = document.data!!["telephone"].toString()
                    this.userEmail = mAuth.currentUser!!.email.toString()
                    this.initializeViews(view)
                } else {
                    Log.d("AccountSettings", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("AccountSettings", "get failed with ", exception)
            }
    }

    private fun initializeViews(view: View) {
        val accountSettingsListView: ListView = view.findViewById(R.id.account_settings_list_view)
        accountSettingsList = this.populateAccountSettingsList()
        accountSettingsListView.adapter = AccountSettingAdapter(requireActivity(), accountSettingsList)

        exitButton = view.findViewById(R.id.btn_sign_out_app)
    }

    private fun populateAccountSettingsList(): ArrayList<AccountSetting> {
        val list: ArrayList<AccountSetting> = ArrayList()

        list.add(AccountSetting("Nome", this.userName, this.userSurname))
        list.add(AccountSetting("Email", this.userEmail))
        list.add(AccountSetting("Celular", this.userCellPhone))

        return list
    }

}