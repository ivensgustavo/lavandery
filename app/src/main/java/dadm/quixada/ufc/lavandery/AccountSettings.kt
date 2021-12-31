package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import dadm.quixada.ufc.lavandery.adapters.AccountSettingAdapter
import dadm.quixada.ufc.lavandery.internalModels.AccountSetting

class AccountSettings : AppCompatActivity() {

    private lateinit var accountSettingsList: ArrayList<AccountSetting>
    private lateinit var  accountSettingsAdapter: AccountSettingAdapter
    private lateinit var btnSignOutApp: TextView
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        accountSettingsList = this.populateAccountSettingsList()
        val accountSettingsListView: ListView = findViewById(R.id.account_settings_list_view)
        accountSettingsAdapter = AccountSettingAdapter(this, accountSettingsList)
        accountSettingsListView.adapter = accountSettingsAdapter

        btnSignOutApp = findViewById(R.id.btn_sign_out_app)
        mAuth = FirebaseAuth.getInstance()

        configureSignOutAppButton()
    }

    private fun populateAccountSettingsList(): ArrayList<AccountSetting> {
        val list: ArrayList<AccountSetting> = ArrayList()

        list.add(AccountSetting("Nome", "Gustavo Ivens", EditName()))
        list.add(AccountSetting("E-mail", "gustavoivens@gmail.com", EditEmailActivity()))
        list.add(AccountSetting("Celular", "+55 88 992436247", EditCellPhoneActivity()))

        return list
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode) {
            R.integer.RESULT_EDIT_NAME -> updateName(data)
            R.integer.RESULT_EDIT_EMAIL -> updateEmail(data)
            R.integer.RESULT_EDIT_CELL_PHONE -> updateCellPhone(data)
        }
    }

    private fun updateName(data: Intent?) {
        val name = data?.extras?.get("name") as String
        val surname = data.extras!!.get("surname") as String
        val fullName = name + " " + surname
        val editedName = AccountSetting("Nome", fullName, EditName())
        this.accountSettingsList[0] = editedName
        this.accountSettingsAdapter.notifyDataSetChanged()
    }

    private fun updateEmail(data: Intent?) {
        val email = data?.extras?.get("email") as String
        val editedEmail = AccountSetting("E-mail", email, EditEmailActivity())
        this.accountSettingsList[1] = editedEmail
        this.accountSettingsAdapter.notifyDataSetChanged()
    }

    private fun updateCellPhone(data: Intent?){
        val cellPhone = data?.extras?.get("cell_phone") as String
        val editedCellPhone = AccountSetting("Celular", cellPhone, EditCellPhoneActivity())
        this.accountSettingsList[2] = editedCellPhone
        this.accountSettingsAdapter.notifyDataSetChanged()
    }

    private fun configureSignOutAppButton(){
        btnSignOutApp.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}