package com.camg_apps.axfactor.ui.menu.ui.infoformula

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.common.MyUtils
import com.camg_apps.axfactor.data.model.Formula
import com.camg_apps.axfactor.data.model.Material
import com.camg_apps.axfactor.databinding.FragmentMaterialListBinding
import com.camg_apps.axfactor.ui.menu.ui.convertir_formula.FormulaConvertirFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout


class InfoFormulaFragment : Fragment(), ConversionDialogFragment.NoticeDialogListener {

    private var _binding: FragmentMaterialListBinding? = null
    val binding get() = _binding!!
    lateinit var adapter: MaterialAdapter
    private var formula: Formula? = null
    private var nuevoTotal = 0.0


    val vmInfoFormula: InfoFormulaViewModel by viewModels()
    companion object{
        const val PARAMETER_FORMULA = "extra_formula"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
          formula =  it.getParcelable(PARAMETER_FORMULA)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMaterialListBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        Toast.makeText(requireContext(), formula!!.materiales.toString(), Toast.LENGTH_SHORT).show()
       adapter = MaterialAdapter(formula!!.materiales!! as ArrayList<Material>, requireContext(), vmInfoFormula, formula!!.codigo)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(requireContext())



        setViewsData()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_info_formula, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_new_material -> {
                showDialogNewMaterial()
            }
            R.id.action_convertir -> {
                showDialogConvertir()
            }
            else -> {

            }
        }

        return super.onOptionsItemSelected(item)

    }

    private fun showDialogConvertir() {
       val  dialogConvertir =  ConversionDialogFragment()
       dialogConvertir.show(childFragmentManager, "SHOW_DIALOG_CONVERSION")
    }


    private fun showDialogNewMaterial() {
        val inflater = requireActivity().layoutInflater;
        val viewDialog = inflater.inflate(R.layout.dialog_new_material, null)

       val diaog = MaterialAlertDialogBuilder(requireContext())
            .setView(viewDialog)
            .setPositiveButton(getString(R.string.fg_newformula_aceptar)){dialog, which ->
                val codigo = viewDialog.findViewById<TextInputLayout>(R.id.textInputC).editText!!.text.toString()
                val gramos = viewDialog.findViewById<TextInputLayout>(R.id.textInputG).editText!!.text.toString().toDouble()
                setNewMaterial(codigo, gramos)
                setDataAdapter()
            }
            .setNegativeButton(getString(R.string.txt_cancelar)){dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setDataAdapter() {
        adapter.setData(formula!!.materiales as ArrayList<Material>)
    }

    private fun setNewMaterial(codigo: String, gramos: Double) {
        vmInfoFormula.setNewMaterial(Material(codigo = codigo, gramos = gramos), MyUtils.getUserUid(requireContext()), formula!!.codigo!!)
    }

    private fun setViewsData() {
        formula?.let {
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

    override fun onDialogPositiveClick(dialog: DialogFragment, nuevosGramos: Double) {
       var nuevaFormula =  vmInfoFormula.convertirFormula(nuevosGramos, formula!!)
        goToConversionFormulaFragment(nuevaFormula, nuevosGramos)
        dialog.dismiss()
    }

    private fun goToConversionFormulaFragment(nuevaFormula: Formula, totalGramos: Double) {
        val bundle = bundleOf(FormulaConvertirFragment.ARG_FORMULA_CONVERTIR to nuevaFormula,
                                FormulaConvertirFragment.ARG_TOTAL_GRAMOS to totalGramos
                                )
        findNavController().navigate(R.id.action_nav_info_to_formulaConvertirFragment, bundle)
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        dialog.dismiss()
    }


}