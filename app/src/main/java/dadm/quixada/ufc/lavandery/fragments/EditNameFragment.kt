package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.logic.UserService

class EditNameFragment : Fragment() {

    private lateinit var nameEditText: TextInputEditText
    private lateinit var surnameEditText: TextInputEditText
    private lateinit var backButton: ImageView
    private lateinit var saveButton: Button
    private val userService = UserService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        fillFields()
    }

    private fun initializeViews(view: View) {
        nameEditText = view.findViewById(R.id.name_input_text_edit)
        surnameEditText = view.findViewById(R.id.surname_input_text_edit)
        saveButton = view.findViewById(R.id.save_name_and_surname_button)
        backButton = view.findViewById(R.id.back_button)

        saveButton.setOnClickListener {
            saveNameAndSurname()
        }

        backButton.setOnClickListener {
            backToPreviousScreen()
        }
    }

    private fun fillFields() {
        val name = arguments?.get("name") as String
        val surname = arguments?.get("surname") as String

        nameEditText.setText(name)
        surnameEditText.setText(surname)
    }

    private fun backToPreviousScreen() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun saveNameAndSurname() {

        userService.updateFullName(
            nameEditText.text.toString().trim(),
            surnameEditText.text.toString().trim()
        ){ result ->
            if(result){
                showConfirmationMessage()
            }else{
                Toast.makeText(
                    requireActivity(),
                    "Ocorreu um erro ao salvar os dados",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showConfirmationMessage() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Dados alterados")
            .setMessage(
                "O nome e sobrenome foram alterados com sucesso"
            )
            .setPositiveButton("Ok") { _, _ ->
                backToPreviousScreen()
            }
            .show()
    }


}