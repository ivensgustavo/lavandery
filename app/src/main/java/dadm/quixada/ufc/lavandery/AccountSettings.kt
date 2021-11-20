package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import dadm.quixada.ufc.lavandery.adapters.AccountSettingAdapter
import dadm.quixada.ufc.lavandery.internalModels.AccountSetting

class AccountSettings : AppCompatActivity() {

    private lateinit var accountSettingsList: ArrayList<AccountSetting>
    private lateinit var  accountSettingsAdapter: AccountSettingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        accountSettingsList = this.populateAccountSettingsList()
        val accountSettingsListView: ListView = findViewById(R.id.account_settings_list_view)
        accountSettingsAdapter = AccountSettingAdapter(this, accountSettingsList)

        accountSettingsListView.adapter = accountSettingsAdapter
    }

    private fun populateAccountSettingsList(): ArrayList<AccountSetting> {
        val list: ArrayList<AccountSetting> = ArrayList()

        list.add(AccountSetting("Nome", "Gustavo Ivens", EditName()))
        list.add(AccountSetting("E-mail", "gustavoivens@gmail.com", EditEmailActivity()))
        list.add(AccountSetting("Celular", "+55 88 992436247", EditName()))

        return list
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode) {
            R.integer.RESULT_EDIT_NAME -> updateName(data)
            R.integer.RESULT_EDIT_EMAIL -> updateEmail(data)
            else -> Toast.makeText(this, "Código de retorno com valor inesperado", Toast.LENGTH_SHORT).show()
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

}