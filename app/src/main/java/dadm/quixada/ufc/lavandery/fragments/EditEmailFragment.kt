package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText
import dadm.quixada.ufc.lavandery.R


class EditEmailFragment : Fragment() {

    private lateinit var backButton: ImageView
    private lateinit var emailEditText: TextInputEditText
    private lateinit var saveEmailButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        fillFields()
    }

    private fun initializeViews(view: View) {
        emailEditText = view.findViewById(R.id.email_input_text_edit)
        saveEmailButton = view.findViewById(R.id.save_email_button)
        backButton = view.findViewById(R.id.back_button)

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
}