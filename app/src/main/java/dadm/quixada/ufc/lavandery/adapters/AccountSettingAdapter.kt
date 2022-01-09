package dadm.quixada.ufc.lavandery.adapters

import android.app.Activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import dadm.quixada.ufc.lavandery.HomeActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.fragments.EditCellPhoneFragment
import dadm.quixada.ufc.lavandery.fragments.EditEmailFragment
import dadm.quixada.ufc.lavandery.fragments.EditNameFragment
import dadm.quixada.ufc.lavandery.internalModels.AccountSetting


class AccountSettingAdapter(
    private val context: Activity,
    private val arrayList: ArrayList<AccountSetting>
) :
    ArrayAdapter<AccountSetting>(context, R.layout.account_settings_list_item, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.account_settings_list_item, null)

        val accountSetting: AccountSetting = arrayList[position]
        val accountSettingName: TextView = view.findViewById(R.id.account_setting_name)
        val accountSettingValue: TextView = view.findViewById(R.id.account_setting_value)
        val accountSettingEditLink: TextView = view.findViewById(R.id.open_account_setting_button)


        accountSettingName.text = accountSetting.name
        accountSettingValue.text = accountSetting.value

        accountSettingEditLink.setOnClickListener {
            when (accountSetting.name) {
                "Nome" -> openEditNameFragment(accountSetting.value)
                "Email" -> openEditEmailFragment(accountSetting.value)
                "Celular" -> openEditCellPhoneFragment(accountSetting.value)
            }
        }

        return view
    }

    private fun openEditNameFragment(fullName: String){
        val bundle = Bundle()
        bundle.putString("full_name", fullName)

        val editNameFragment = EditNameFragment()
        editNameFragment.arguments = bundle

        val activity = context as HomeActivity

        activity.supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_screens, editNameFragment)
            addToBackStack("edit_name_fragment")
            commit()
        }
    }

    private fun openEditEmailFragment(email: String){
        val bundle = Bundle()
        bundle.putString("email", email)

        val editEmailFragment = EditEmailFragment()
        editEmailFragment.arguments = bundle

        val activity = context as HomeActivity

        activity.supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_screens, editEmailFragment)
            addToBackStack("edit_email_fragment")
            commit()
        }
    }

    private fun openEditCellPhoneFragment(cellPhone: String){
        val bundle = Bundle()
        bundle.putString("cell_phone", cellPhone)

        val editCellPhoneFragment = EditCellPhoneFragment()
        editCellPhoneFragment.arguments = bundle

        val activity = context as HomeActivity

        activity.supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_screens, editCellPhoneFragment)
            addToBackStack("edit_cell_phone_fragment")
            commit()
        }
    }
}