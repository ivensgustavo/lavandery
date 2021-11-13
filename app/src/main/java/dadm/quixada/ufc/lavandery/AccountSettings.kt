package dadm.quixada.ufc.lavandery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import dadm.quixada.ufc.lavandery.adapters.AccountSettingAdapter
import dadm.quixada.ufc.lavandery.internalModels.AccountSetting

class AccountSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        val accountSettingsList: ArrayList<AccountSetting> = this.populateAccountSettingsList()
        val accountSettingsListView: ListView = findViewById(R.id.account_settings_list_view)
        val accountSettingsAdapter = AccountSettingAdapter(this, accountSettingsList)

        accountSettingsListView.adapter = accountSettingsAdapter
    }

    private fun populateAccountSettingsList(): ArrayList<AccountSetting> {
        val list: ArrayList<AccountSetting> = ArrayList()

        list.add(AccountSetting("Nome", "Gustavo Ivens"))
        list.add(AccountSetting("E-mail", "gustavoivens@gmail.com"))
        list.add(AccountSetting("Celular", "+55 88 992436247"))

        return list
    }
}