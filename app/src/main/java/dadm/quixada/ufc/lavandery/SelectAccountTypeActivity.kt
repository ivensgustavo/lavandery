package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SelectAccountTypeActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var createAccountButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_account_type)

        mAuth = FirebaseAuth.getInstance()
        createAccountButton = findViewById(R.id.continue_to_address_register)

        this.configureContinueButton()
    }

    override fun onStart() {
        super.onStart()
        val name = intent.extras!!.get("name").toString()

        Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
    }

    private fun configureContinueButton(){

        createAccountButton.setOnClickListener {
            //val intent = Intent(this, AddressRegistrationActivity::class.java)
            //startActivity(intent)
            createUser()
        }
    }

    private fun createUser(){

        val email = intent.extras!!.get("email").toString()
        val password = intent.extras!!.get("password").toString()

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                val exception = task.exception
                if(task.isSuccessful){
                    Toast.makeText(this, "Conta criada com sucesso", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Ocorreu um erro ao criar a conta", Toast.LENGTH_SHORT).show()
                    Log.w("SelectAccountActivity", exception!!.message.toString())
                }
            }

    }
}