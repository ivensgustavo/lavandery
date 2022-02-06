package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import dadm.quixada.ufc.lavandery.logic.AddressService
import java.util.*

class AddressRegistrationActivity : AppCompatActivity() {

    private lateinit var streetEditText: TextInputEditText
    private lateinit var numberEditText: TextInputEditText
    private lateinit var cepEditText: TextInputEditText
    private lateinit var complementEditText: TextInputEditText
    private lateinit var buttonSaveAddress: Button
    private val addressService = AddressService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_registration)

        initializeViews()
    }

    private fun initializeViews() {
        streetEditText = findViewById(R.id.address_registration_street)
        numberEditText = findViewById(R.id.address_registration_number)
        cepEditText = findViewById(R.id.address_registration_cep)
        complementEditText = findViewById(R.id.address_registration_complement)
        buttonSaveAddress = findViewById(R.id.button_save_address)

        buttonSaveAddress.setOnClickListener {
            saveAddressInDB()
        }
    }

    private fun saveAddressInDB() {

        val id = UUID.randomUUID().toString()
        val street = streetEditText.text.toString()
        val number = numberEditText.text.toString().toInt()
        val cep = cepEditText.text.toString().toInt()
        val complement = complementEditText.text.toString()

        addressService.addAddress(id, street, number, cep, complement) { result ->
            if (result) {
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
                    "Ocorreu um erro ao adicionar o endereço.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }


}