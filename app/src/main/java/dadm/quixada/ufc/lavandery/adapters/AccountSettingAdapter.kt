package dadm.quixada.ufc.lavandery.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
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

            accountSettingName.text = accountSetting.name
            accountSettingValue.text = accountSetting.value

            return view
        }
}