package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class RegistrationActivity : AppCompatActivity() {

    private lateinit var continueButton: Button
    private lateinit var signInLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        this.initializeViews()
        this.configureContinueButton()
        this.configureSignInLink()
    }

    private fun initializeViews(){
        continueButton = findViewById(R.id.continue_button)
        signInLink = findViewById(R.id.sign_in_link)
    }

    private fun configureSignInLink() {
        signInLink.setOnClickListener {
            val loginIntent = Intent(this, MainActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun configureContinueButton(){
        continueButton.setOnClickListener {
            val intent = Intent(this, SelectAccountTypeActivity::class.java)
            startActivity(intent)
        }
    }
}