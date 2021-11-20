package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class EditName : AppCompatActivity() {

    private lateinit var nameEditText: TextInputEditText
    private lateinit var surnameEditText: TextInputEditText
    private lateinit var saveNameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_name)

        this.initializeViews()
        this.fillFields()
        this.configureSaveNameButton()
    }

    private fun initializeViews(){
        nameEditText = findViewById(R.id.name_input_text_edit)
        surnameEditText = findViewById(R.id.surname_input_text_edit)
        saveNameButton = findViewById(R.id.save_name_button)
    }

    private fun fillFields() {

        val fullName = intent.extras?.get("name") as String
        val names = fullName.split(" ").toTypedArray()

        nameEditText.setText(names[0])
        surnameEditText.setText(names[1])
    }

    private fun configureSaveNameButton() {
        saveNameButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("name", nameEditText.text.toString())
            intent.putExtra("surname", surnameEditText.text.toString())

            setResult(R.integer.RESULT_EDIT_NAME, intent)
            finish()
        }
    }

}