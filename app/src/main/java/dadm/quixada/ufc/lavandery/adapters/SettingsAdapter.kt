package dadm.quixada.ufc.lavandery.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.internalModels.SettingItem

class SettingsAdapter(private val context: Activity, private val arrayList: ArrayList<SettingItem>)
    : ArrayAdapter<SettingItem>(context, R.layout.settings_list_item, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.settings_list_item, null)

        val setting = arrayList[position]
        val settingIcon: ImageView = view.findViewById(R.id.setting_icon)
        val settingName: TextView = view.findViewById(R.id.setting_name)

        settingIcon.setImageResource(setting.settingIconId)
        settingName.text = setting.settingName

        return view
    }
}