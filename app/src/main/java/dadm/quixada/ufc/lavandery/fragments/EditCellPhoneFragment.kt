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


class EditCellPhoneFragment : Fragment() {

    private lateinit var cellPhoneEditText: TextInputEditText
    private lateinit var saveCellPhoneButton: Button
    private lateinit var backButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_telephone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        fillFields()
    }

    private fun initializeViews(view: View){
        cellPhoneEditText = view.findViewById(R.id.cell_phone_input_text_edit)
        saveCellPhoneButton = view.findViewById(R.id.save_cell_phone_button)
        backButton = view.findViewById(R.id.back_button)

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


}