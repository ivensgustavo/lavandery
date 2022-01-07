package dadm.quixada.ufc.lavandery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dadm.quixada.ufc.lavandery.R


class NewOrderFragment : Fragment() {

    private lateinit var counterCamisas: CountPartsFragment
    private lateinit var counterCamisetas: CountPartsFragment
    private lateinit var counterBermudas: CountPartsFragment
    private lateinit var counterShortsLeves: CountPartsFragment
    private lateinit var counterShortsJeans: CountPartsFragment
    private lateinit var counterCalcas: CountPartsFragment
    private lateinit var counterSaias: CountPartsFragment
    private lateinit var counterVestidos: CountPartsFragment
    private lateinit var counterAgasalhos: CountPartsFragment
    private lateinit var qtyOrderItemsTextView: TextView
    private lateinit var orderValueTextView: TextView
    private var qtyOrderItems: Int = 0
    private var totalOrderValue: Float = 0.0f


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationMenu =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)

        bottomNavigationMenu.visibility = View.GONE

        initializeViews(view)
        initializeCounterFragments()
        changeCounterFragments()
    }

    private fun initializeViews(view: View){
        qtyOrderItemsTextView = view.findViewById(R.id.items_in_new_order)
        orderValueTextView = view.findViewById(R.id.total_value_in_new_order)
    }

    private fun initializeCounterFragments(){
        counterCamisas =
            childFragmentManager.findFragmentById(R.id.counter_camisas) as CountPartsFragment

        counterCamisetas=
            childFragmentManager.findFragmentById(R.id.counter_camisetas) as CountPartsFragment

        counterBermudas =
            childFragmentManager.findFragmentById(R.id.counter_bermudas) as CountPartsFragment

        counterShortsLeves =
            childFragmentManager.findFragmentById(R.id.counter_shorts_leves) as CountPartsFragment

        counterShortsJeans =
            childFragmentManager.findFragmentById(R.id.counter_shorts_jeans) as CountPartsFragment

        counterCalcas =
            childFragmentManager.findFragmentById(R.id.counter_calcas) as CountPartsFragment

        counterSaias =
            childFragmentManager.findFragmentById(R.id.counter_saias) as CountPartsFragment

        counterVestidos =
            childFragmentManager.findFragmentById(R.id.counter_vestidos) as CountPartsFragment

        counterAgasalhos =
            childFragmentManager.findFragmentById(R.id.counter_agasalhos) as CountPartsFragment
    }

    private fun changeCounterFragments() {
        counterCamisas.setKindOfClothes("Camisas")
        counterCamisas.setPricePerPiece(3.0f)

        counterCamisetas.setKindOfClothes("Camisetas")
        counterCamisetas.setPricePerPiece(3.0f)

        counterBermudas.setKindOfClothes("Bermudas")
        counterBermudas.setPricePerPiece(5.0f)

        counterShortsLeves.setKindOfClothes("Shorts Leves")
        counterShortsLeves.setPricePerPiece(2.0f)

        counterShortsJeans.setKindOfClothes("Shorts Jeans")
        counterShortsJeans.setPricePerPiece(4.0f)

        counterCalcas.setKindOfClothes("Calças")
        counterCalcas.setPricePerPiece(5.0f)

        counterSaias.setKindOfClothes("Saias")
        counterSaias.setPricePerPiece(2.0f)

        counterVestidos.setKindOfClothes("Vestidos")
        counterVestidos.setPricePerPiece(6.0f)

        counterAgasalhos.setKindOfClothes("Agasalhos")
        counterShortsJeans.setPricePerPiece(6.0f)
    }

    fun getQtyOrderItems():Int {
        return qtyOrderItems
    }

    fun getTotalOrderValue(): Float {
        return totalOrderValue
    }

    fun setQtyOrderItems(items: Int){
        qtyOrderItems = items
        qtyOrderItemsTextView.text = items.toString()
    }

    fun setTotalOrderValue(value: Float){
        totalOrderValue = value
        orderValueTextView.text = String.format("%.2f", totalOrderValue)
    }
}