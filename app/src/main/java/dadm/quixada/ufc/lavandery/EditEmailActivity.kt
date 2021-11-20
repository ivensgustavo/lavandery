package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText

class EditEmailActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var emailEditText: TextInputEditText
    private lateinit var saveEmailButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_email)

        this.initializeViews()
        this.configureSaveEmailButton()
        this.configureBackButton()

        val email: String = intent.extras?.get("email") as String
        this.emailEditText.setText(email)
    }

    private fun initializeViews() {
        emailEditText = findViewById(R.id.email_input_text_edit)
        saveEmailButton = findViewById(R.id.save_email_button)
        backButton = this.findViewById(R.id.back_button)
    }

    private fun configureSaveEmailButton() {
        saveEmailButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("email", emailEditText.text.toString())
            setResult(R.integer.RESULT_EDIT_EMAIL, intent)
            finish()
        }
    }

    private fun configureBackButton() {
        backButton.setOnClickListener {
            finish()
        }
    }
}