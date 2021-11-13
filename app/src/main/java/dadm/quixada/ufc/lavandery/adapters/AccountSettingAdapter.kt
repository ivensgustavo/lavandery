package dadm.quixada.ufc.lavandery.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.internalModels.AccountSetting
import dadm.quixada.ufc.lavandery.internalModels.ConsumerPreference

class AccountSettingAdapter(private val context: Activity, private val arrayList: ArrayList<AccountSetting>):
    ArrayAdapter<AccountSetting>(context, R.layout.account_settings_list_item ,arrayList) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater: LayoutInflater = LayoutInflater.from(context)
            val view: View = inflater.inflate(R.layout.account_settings_list_item, null)

            val accountSetting: AccountSetting = arrayList[position]
            val accountSettingName: TextView = view.findViewById(R.id.account_setting_name)
            val accountSettingValue: TextView = view.findViewById(R.id.account_setting_value)
            val accountSettingActivity: Activity = accountSetting.activity
            val accountSettingEditLink: TextView = view.findViewById(R.id.open_account_setting_button)


            accountSettingName.text = accountSetting.name
            accountSettingValue.text = accountSetting.value

            accountSettingEditLink.setOnClickListener {


                when(accountSetting.name) {
                    "Nome" -> {
                        val intent = Intent(context, accountSettingActivity::class.java)
                        intent.putExtra("name", accountSetting.value)
                        context.startActivity(intent)
                    }
                    else -> {
                        Toast.makeText(context, "Ainda não implementado", Toast.LENGTH_SHORT).show()
                    }
                }


            }

            return view
        }
}