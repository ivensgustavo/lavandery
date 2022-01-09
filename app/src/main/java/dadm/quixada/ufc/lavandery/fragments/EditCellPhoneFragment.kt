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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.lavandery.R


class EditCellPhoneFragment : Fragment() {

    private lateinit var cellPhoneEditText: TextInputEditText
    private lateinit var saveCellPhoneButton: Button
    private lateinit var backButton: ImageView
    private lateinit var  mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_telephone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        initializeViews(view)
        fillFields()
    }

    private fun initializeViews(view: View){
        cellPhoneEditText = view.findViewById(R.id.cell_phone_input_text_edit)
        saveCellPhoneButton = view.findViewById(R.id.save_cell_phone_button)
        backButton = view.findViewById(R.id.back_button)

        saveCellPhoneButton.setOnClickListener {
            saveCellPhone()
        }

        backButton.setOnClickListener {
            backToPreviousScreen()
        }
    }

    private fun fillFields() {
        val cellPhone: String = arguments?.get("cell_phone") as String
        this.cellPhoneEditText.setText(cellPhone)
    }

    private fun backToPreviousScreen() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun saveCellPhone(){
        val db = Firebase.firestore
        val currentUserId = mAuth.currentUser!!.uid

        db.collection("users").document(currentUserId)
            .update("telephone", cellPhoneEditText.text.toString())
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    showConfirmationMessage()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Ocorreu um ao salvar o número de celular",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun showConfirmationMessage() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Dados alterados")
            .setMessage(
                "O número de celular foi alterado com sucesso"
            )
            .setPositiveButton("Ok") { dialog, which ->
                backToPreviousScreen()
            }
            .show()
    }


}