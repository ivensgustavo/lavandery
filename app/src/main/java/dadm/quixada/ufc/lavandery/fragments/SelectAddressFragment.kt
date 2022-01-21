package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.AddressAdapter
import dadm.quixada.ufc.lavandery.models.Address

class SelectAddressFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_address, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addressListView: ListView = view.findViewById(R.id.my_address_list_view)
        val addressList: ArrayList<Address> = ArrayList()
        addressList.add(
            Address(
                "1",
                "Rua Ministro Antonio Coelho",
                719,
                62370000,
                "Em frente a EMEB Dep Júlio Filizola"
            )
        )

        addressList.add(
            Address(
                "2",
                "Rua Paulo Sarazati",
                627,
                62370000,
                "Próximo a UPA"
            )
        )

        val adapter = AddressAdapter(requireActivity(), addressList)
        addressListView.adapter = adapter

        addressListView.setOnItemClickListener { adapterView, view, position, l ->
            adapter.setCurrentAddress(addressList[position].id)
            adapter.notifyDataSetChanged()
            this.dismiss()
        }
    }
}