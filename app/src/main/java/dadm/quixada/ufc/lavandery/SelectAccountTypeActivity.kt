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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.models.Address
import dadm.quixada.ufc.lavandery.models.ServicePreferences
import dadm.quixada.ufc.lavandery.models.User

class SelectAccountTypeActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var createAccountButton: Button
    private lateinit var radioGroupAccountType: RadioGroup
    private lateinit var accountTypeRadioSelected: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_account_type)

        mAuth = FirebaseAuth.getInstance()
        createAccountButton = findViewById(R.id.continue_to_address_register)

        this.initializeViews()
        this.configureContinueButton()
    }

    private fun initializeViews() {
        radioGroupAccountType = findViewById(R.id.radio_group_select_account_type)
    }

    private fun configureContinueButton() {

        createAccountButton.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {

        val name = intent.extras!!.get("name").toString()
        val surname = intent.extras!!.get("surname").toString()
        val telephone = intent.extras!!.get("telephone").toString()
        val email = intent.extras!!.get("email").toString()
        val password = intent.extras!!.get("password").toString()

        val preferences = ServicePreferences(
            ironed_clothes = false,
            orders_at_reception = false,
            scented_clothes = false,
            t_shirts_on_hanger = false)

        accountTypeRadioSelected = findViewById(radioGroupAccountType.checkedRadioButtonId)
        val accountType: String = accountTypeRadioSelected.text.toString()


        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                val exception = task.exception
                if (task.isSuccessful) {
                    val currentUserId = mAuth.currentUser!!.uid
                    val newUser = User(name, surname, telephone, accountType)

                    val db = Firebase.firestore
                    val userRef = db.collection("users").document(currentUserId)

                    db.runBatch { batch ->
                        batch.set(userRef, newUser)
                        batch.update(userRef, "preferences", preferences)
                    }.addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Sua conta foi criada com sucesso.",
                                Toast.LENGTH_LONG
                            ).show()

                            val addressIntent = Intent(this, AddressRegistrationActivity::class.java)
                            startActivity(addressIntent)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Houve um ero ao criar a conta. Tente novamente",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                } else {
                    Toast.makeText(
                        this,
                        "Ocorreu um erro ao criar a conta. Tente novamente",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.w("SelectAccountActivity", exception!!.message.toString())
                }
            }
    }

    private fun saveServicesPreferences(){
        val preferences = ServicePreferences(
            ironed_clothes = false,
            orders_at_reception = false,
            scented_clothes = false,
            t_shirts_on_hanger = false)

        val db = Firebase.firestore
        val userId = mAuth.currentUser!!.uid

        db.collection("users").document(userId)
            .update("preferences", preferences)
            .addOnSuccessListener {

            }

    }
}