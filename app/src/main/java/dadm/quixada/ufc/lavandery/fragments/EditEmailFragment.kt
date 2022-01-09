package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dadm.quixada.ufc.lavandery.R


class EditEmailFragment : Fragment() {

    private lateinit var backButton: ImageView
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var saveEmailButton: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        initializeViews(view)
        fillFields()
    }

    private fun initializeViews(view: View) {
        emailEditText = view.findViewById(R.id.email_input_text_edit)
        passwordEditText = view.findViewById(R.id.password_input_text_edit)
        saveEmailButton = view.findViewById(R.id.save_email_button)
        backButton = view.findViewById(R.id.back_button)

        saveEmailButton.setOnClickListener {
            saveEmail()
        }

        backButton.setOnClickListener {
            backToPreviousScreen()
        }
    }

    private fun fillFields() {
        val email = arguments?.get("email") as String

        emailEditText.setText(email)
    }

    private fun backToPreviousScreen() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun saveEmail() {


        if (!validateData()) {
            Toast.makeText(
                requireActivity(),
                "Preecha todos os campos",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val user = mAuth.currentUser

        val credential = EmailAuthProvider
            .getCredential(user!!.email.toString().trim(), passwordEditText.text.toString().trim())

        user.reauthenticate(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.updateEmail(emailEditText.text.toString().trim())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                showConfirmationMessage()
                            } else {
                                Toast.makeText(
                                    requireActivity(),
                                    "Ocorreu um erro ao atualizar o email. Verifique os dados e tente novamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }


    }

    private fun validateData(): Boolean {
        if (emailEditText.text.toString().trim().isEmpty()) {
            return false
        }

        if (passwordEditText.text.toString().trim().isEmpty()) {
            return false
        }

        return true
    }

    private fun showConfirmationMessage() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Dados alterados")
            .setMessage(
                "O endereço de email foi alterado com sucesso"
            )
            .setPositiveButton("Ok") { dialog, which ->
                backToPreviousScreen()
            }
            .show()
    }
}