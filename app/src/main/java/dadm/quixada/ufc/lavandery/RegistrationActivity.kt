package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class RegistrationActivity : AppCompatActivity() {

    private lateinit var continueButton: Button
    private lateinit var signInLink: TextView
    private lateinit var nameEditText: TextInputEditText
    private lateinit var surnameEditText: TextInputEditText
    private lateinit var telephoneEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText

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
        nameEditText = findViewById(R.id.registration_name)
        surnameEditText = findViewById(R.id.registration_surname)
        telephoneEditText = findViewById(R.id.registration_telephone)
        emailEditText = findViewById(R.id.registration_email)
        passwordEditText = findViewById(R.id.registration_password)
    }

    private fun validateData(): Boolean{

        var isValid = true

        if(nameEditText.text.isNullOrEmpty()){
            nameEditText.error = "É necessário informar um valor para o nome"
            isValid = false
        }
        if(surnameEditText.text.isNullOrEmpty()){
            surnameEditText.error = "É necessário informar um valor para o sobrenome"
            isValid = false
        }
        if(telephoneEditText.text.isNullOrEmpty()){
            telephoneEditText.error = "É necessário informar um número de telefone"
            isValid = false
        }
        if(emailEditText.text.isNullOrEmpty() or !Patterns.EMAIL_ADDRESS.matcher(this.emailEditText.text.toString()).matches()){
            emailEditText.error = "É necessário informar um email válido"
            isValid = false
        }
        if(passwordEditText.text.isNullOrEmpty() or (passwordEditText.text!!.length < 6)){
            passwordEditText.error = "A senha deve ter no mínimo 6 caracteres"
            isValid = false
        }

        return isValid
    }

    private fun configureSignInLink() {
        signInLink.setOnClickListener {
            val loginIntent = Intent(this, MainActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun configureContinueButton(){
        continueButton.setOnClickListener {
            val isValid = validateData()

            if(isValid){
                val intent = Intent(this, SelectAccountTypeActivity::class.java)
                intent.putExtra("name", nameEditText.text)
                intent.putExtra("surname", surnameEditText.text)
                intent.putExtra("telephone", telephoneEditText.text)
                intent.putExtra("email", emailEditText.text)
                intent.putExtra("password", passwordEditText.text)
                startActivity(intent)
            }
        }
    }
}