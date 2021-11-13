package dadm.quixada.ufc.lavandery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class EditName : AppCompatActivity() {

    lateinit var nameEditText: TextInputEditText
    lateinit var surnameEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_name)

        nameEditText = findViewById(R.id.name_input_text_edit)
        surnameEditText = findViewById(R.id.surname_input_text_edit)

        fillFields()
    }

    private fun fillFields() {

        val fullName = intent.extras?.get("name") as String
        val names = fullName.split(" ").toTypedArray()

        nameEditText.setText(names[0])
        surnameEditText.setText(names[1])
    }
}