package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        configureSignInLink()
    }

    private fun configureSignInLink() {
        val signInLink: TextView = findViewById(R.id.sign_in_link)

        signInLink.setOnClickListener {
            val loginIntent = Intent(this, MainActivity::class.java)
            startActivity(loginIntent)
        }
    }
}