package com.camg_apps.axfactor.ui.menu.ui.convertir_formula

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.data.model.Formula
import com.camg_apps.axfactor.databinding.FragmentMaterialConvertirListBinding


class FormulaConvertirFragment : Fragment() {

    private var formulaData: Formula? = null
    private var _binding: FragmentMaterialConvertirListBinding? = null
    val binding get() = _binding!!
    lateinit var adapter: MaterialConvertirAdapter

    companion object{
        const val  ARG_FORMULA_CONVERTIR = "formula_convertir_data"
        const val ARG_TOTAL_GRAMOS = "formula_total_gramos"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            formulaData = it.getParcelable<Formula>(ARG_FORMULA_CONVERTIR)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMaterialConvertirListBinding.inflate(inflater, container, false)

        adapter = MaterialConvertirAdapter(formulaData!!.materiales!!)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(requireContext())

        setHasOptionsMenu(true)

        setViewsData()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_convertir, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_new_formula -> {
                Toast.makeText(requireContext(), "Pendiente de implementar", Toast.LENGTH_SHORT).show()
            }
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setViewsData() {
        formulaData?.let {
            binding.textViewCodigo.text = it.codigo
            binding.textViewMarca.text = it.armadora
            binding.textViewLinea.text = it.linea
            binding.imageViewColor.setBackgroundColor(it.color!!.toInt())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}