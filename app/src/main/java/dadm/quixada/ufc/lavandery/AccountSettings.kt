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
        list.add(AccountSetting("E-mail", "gustavoivens@gmail.com", EditName()))
        list.add(AccountSetting("Celular", "+55 88 992436247", EditName()))

        return list
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == R.integer.RESULT_EDIT_NAME) {
            val name = data?.extras?.get("name") as String
            val surname = data.extras!!.get("surname") as String
            val fullName = name + " " + surname
            val editedName = AccountSetting("Nome", fullName, EditName())
            this.accountSettingsList[0] = editedName
            this.accountSettingsAdapter.notifyDataSetChanged()
        }
    }

}