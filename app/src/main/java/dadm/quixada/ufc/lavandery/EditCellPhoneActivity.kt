package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText

class EditCellPhoneActivity : AppCompatActivity() {

    private lateinit var cellPhoneEditText: TextInputEditText
    private lateinit var saveCellPhoneButton: Button
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_cell_phone)

        this.initializeViews()
        this.fillFields()
        this.configureSaveCellPhoneButton()
        this.configureBackButton()
    }

    private fun initializeViews(){
        cellPhoneEditText = findViewById(R.id.cell_phone_input_text_edit)
        saveCellPhoneButton = findViewById(R.id.save_cell_phone_button)
        backButton = findViewById(R.id.back_button)
    }

    private fun fillFields() {
        val cellPhone: String = intent.extras?.get("cell_phone") as String
        this.cellPhoneEditText.setText(cellPhone)
    }

    private fun configureSaveCellPhoneButton() {
        this.saveCellPhoneButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("cell_phone", cellPhoneEditText.text.toString())
            setResult(R.integer.RESULT_EDIT_CELL_PHONE, intent)
            finish()
        }
    }

    private fun configureBackButton() {
        this.backButton.setOnClickListener {
            finish()
        }
    }


}