package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.models.Address
import java.util.*

class AddressRegistrationActivity : AppCompatActivity() {

    private lateinit var streetEditText: TextInputEditText
    private lateinit var numberEditText: TextInputEditText
    private lateinit var cepEditText: TextInputEditText
    private lateinit var complementEditText: TextInputEditText
    private lateinit var buttonSaveAddress: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_registration)

        initializeViews()
        mAuth = FirebaseAuth.getInstance()

        configureSaveAddressButton()
    }

    private fun initializeViews() {
        streetEditText = findViewById(R.id.address_registration_street)
        numberEditText = findViewById(R.id.address_registration_number)
        cepEditText = findViewById(R.id.address_registration_cep)
        complementEditText = findViewById(R.id.address_registration_complement)
        buttonSaveAddress = findViewById(R.id.button_save_address)
    }

    private fun saveAddress() {
        val address = Address(
            UUID.randomUUID().toString(),
            streetEditText.text.toString(),
            numberEditText.text.toString().toInt(),
            cepEditText.text.toString().toInt(),
            complementEditText.text.toString()
        )

        val currentUserId = mAuth.currentUser!!.uid

        val db = Firebase.firestore

        db.collection("users").document(currentUserId)
            .update("addresses", FieldValue.arrayUnion(address))
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Endereço adicionado com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()

                    val homeIntent = Intent(this, HomeActivity::class.java)
                    startActivity(homeIntent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Ocorreu um erro ao salvar o endereço. Tente novamente",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun configureSaveAddressButton() {
        buttonSaveAddress.setOnClickListener {
            saveAddress()
        }
    }


}