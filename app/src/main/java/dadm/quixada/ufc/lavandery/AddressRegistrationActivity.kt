package dadm.quixada.ufc.lavandery

import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import dadm.quixada.ufc.lavandery.logic.AddressService
import java.util.*
import kotlin.collections.HashMap

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
        val number = numberEditText.text.toString().trim().toInt()
        val cep = cepEditText.text.toString().trim().toInt()
        val complement = complementEditText.text.toString().trim()
        val geoPoint = getGeoPoint(street, number.toString(), cep.toString())


        Log.d("Extras", intent.extras.toString())
        val accountType = intent.extras!!.get("accountType").toString()

        if (accountType == "Consumidor") {
            addressService.addAddressToConsumer(
                id, street, number, cep, complement,
                geoPoint["latitude"]!!, geoPoint["longitude"]!!
            ) { result ->
                if (result) {
                    showSuccessMessage()
                    openHomeActivity()
                } else {
                    showErrorMessage()
                }
            }
        } else {
            addressService.addAddressToProvider(
                id, street, number, cep, complement,
                geoPoint["latitude"]!!, geoPoint["longitude"]!!
            ) { result ->
                if (result) {
                    showSuccessMessage()
                    openHomeActivity()
                } else {
                    showErrorMessage()
                }
            }
        }

    }

    private fun openHomeActivity() {
        val homeIntent = Intent(this, HomeActivity::class.java)
        startActivity(homeIntent)
        finish()
    }

    private fun showSuccessMessage() {
        Toast.makeText(
            this,
            "Endereço adicionado com sucesso",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showErrorMessage() {
        Toast.makeText(
            this,
            "Ocorreu um erro ao adicionar o endereço.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun getGeoPoint(street: String, number: String, cep: String): HashMap<String, Double> {
        val geocoder = Geocoder(this, Locale("pt", "BR"))
        val results = geocoder.getFromLocationName("$street, $number, $cep", 1)

        val geoPoint = HashMap<String, Double>()
        geoPoint["latitude"] = results[0].latitude
        geoPoint["longitude"] = results[0].longitude
        return geoPoint
    }


}