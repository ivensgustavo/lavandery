package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import com.google.firebase.auth.FirebaseAuth
import dadm.quixada.ufc.lavandery.logic.UserService
import dadm.quixada.ufc.lavandery.models.User

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private val userService = UserService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser


        Handler().postDelayed({
            if(user != null){
                userService.getUser(user.uid){ loggedUser ->
                    if(loggedUser != null){
                        browseTheUser(loggedUser)
                    }
                }
            }else {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
        }, 2000)
    }

    private fun browseTheUser(user: User){
        if(user.accountType == "Consumidor de serviços de lavanderia"){
            val consumerHomeIntent = Intent(this, HomeActivity::class.java)
            startActivity(consumerHomeIntent)
            finish()
        }else{
            val providerHomeIntent = Intent(this, ProviderHomeActivity::class.java)
            startActivity(providerHomeIntent)
            finish()
        }
    }
}