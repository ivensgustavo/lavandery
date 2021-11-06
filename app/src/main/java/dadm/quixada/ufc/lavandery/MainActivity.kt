package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEnter: Button = findViewById(R.id.enter_login_button)
        btnEnter.setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
        }

        configureSignUpLink()
    }

    private fun configureSignUpLink() {
        val signUpLink: TextView = findViewById(R.id.sign_up_link)

        signUpLink.setOnClickListener {
            val registrationIntent = Intent(this, RegistrationActivity::class.java)
            startActivity(registrationIntent)
        }
    }
}