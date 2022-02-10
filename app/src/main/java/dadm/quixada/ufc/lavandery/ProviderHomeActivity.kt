package dadm.quixada.ufc.lavandery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import dadm.quixada.ufc.lavandery.fragments.ProviderHomeFragment
import dadm.quixada.ufc.lavandery.fragments.SettingsFragment


class ProviderHomeActivity : AppCompatActivity() {

    private val providerHomeFragment = ProviderHomeFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_home)

        val bundle = Bundle()
        bundle.putString("accountType", "Provedor")
        settingsFragment.arguments = bundle
        setBottomNavigationActions()
        changeCurrentFragment(providerHomeFragment)
    }

    private fun setBottomNavigationActions() {
        val bottomNavigationMenu: BottomNavigationView = findViewById(R.id.bottom_navigation_menu_provider)
        bottomNavigationMenu.visibility = View.VISIBLE
        bottomNavigationMenu.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.provider_home_nav_button -> changeCurrentFragment(providerHomeFragment)
                R.id.provider_settings_nav_button -> changeCurrentFragment(settingsFragment)
            }
            true
        }

    }

    private fun changeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_screens, fragment)
            commit()
        }
    }
}