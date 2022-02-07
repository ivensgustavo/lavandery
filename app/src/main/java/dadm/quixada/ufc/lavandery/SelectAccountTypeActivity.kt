package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import dadm.quixada.ufc.lavandery.logic.ConsumerService
import dadm.quixada.ufc.lavandery.logic.ProviderService


class SelectAccountTypeActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var createAccountButton: Button
    private lateinit var radioGroupAccountType: RadioGroup
    private val consumerService = ConsumerService()
    private val providerService = ProviderService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_account_type)

        this.initializeViews()
    }

    private fun initializeViews() {
        radioGroupAccountType = findViewById(R.id.radio_group_select_account_type)
        createAccountButton = findViewById(R.id.continue_to_address_register)

        createAccountButton.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {

        val name = intent.extras!!.get("name").toString()
        val surname = intent.extras!!.get("surname").toString()
        val telephone = intent.extras!!.get("telephone").toString()
        val accountType: String =
            findViewById<RadioButton>(radioGroupAccountType.checkedRadioButtonId).text.toString()

        val email = intent.extras!!.get("email").toString()
        val password = intent.extras!!.get("password").toString()


        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val id = mAuth.currentUser!!.uid

                    if(accountType == "Consumidor de serviços de lavanderia"){
                        consumerService.createConsumer(id, name, surname, email, telephone, accountType){  result ->
                            if(result) openAddressScreen("Consumidor")
                            else showErrorMessage()
                        }
                    }else{
                        providerService.createProvider(id, name, surname, email, telephone, accountType){ result ->
                            if(result) {
                                Log.d("Select Activy","Chegou aqui")
                                openAddressScreen("Provedor")
                            }
                            else showErrorMessage()
                        }
                    }
                } else {
                    showErrorMessage()
                }
            }
    }

    private fun openAddressScreen(accountType: String){
        val addressIntent = Intent(this, AddressRegistrationActivity::class.java)
        addressIntent.putExtra("accountType", accountType)
        startActivity(addressIntent)
        finish()
    }

    private fun showErrorMessage() {
        Toast.makeText(
            this,
            "Não foi possível criar a conta. Tente novamente",
            Toast.LENGTH_LONG
        ).show()
    }

}