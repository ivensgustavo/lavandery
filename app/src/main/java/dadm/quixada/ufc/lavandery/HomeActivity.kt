package dadm.quixada.ufc.lavandery


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import dadm.quixada.ufc.lavandery.fragments.HomeFragment
import dadm.quixada.ufc.lavandery.fragments.MyOrdersFragment
import dadm.quixada.ufc.lavandery.fragments.SettingsFragment


class HomeActivity : AppCompatActivity() {

    private val homeFragment: Fragment = HomeFragment()
    private val myRequestFragment: Fragment = MyOrdersFragment()
    private val settingsFragment: Fragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        changeCurrentFragment(homeFragment)
        setBottomNavigationActions()


    }

    private fun setBottomNavigationActions() {
        val bottomNavigationMenu: BottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        bottomNavigationMenu.visibility = View.VISIBLE
        bottomNavigationMenu.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home_nav_button -> changeCurrentFragment(homeFragment)
                R.id.my_requests_nav_button -> changeCurrentFragment(myRequestFragment)
                else -> changeCurrentFragment(settingsFragment)
            }
            true
        }

    }

    fun changeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_screens, fragment)
            commit()
        }
    }

    fun cleanBackStack(){
        for(i in 0 until supportFragmentManager.backStackEntryCount){
            supportFragmentManager.popBackStack()
        }
    }

    fun showBottomNavigation(){
        val bottomNavigationMenu: BottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        bottomNavigationMenu.visibility = View.VISIBLE
    }
}