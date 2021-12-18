package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SelectAccountTypeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_account_type)

        this.configureContinueButton()
    }

    private fun configureContinueButton(){
        val continueButton: Button = findViewById(R.id.continue_to_address_register)
        continueButton.setOnClickListener {
            val intent = Intent(this, AddressRegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}