package com.camg_apps.axfactor.ui.menu.ui.infoformula

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.common.MyUtils
import com.camg_apps.axfactor.data.model.Formula
import com.camg_apps.axfactor.data.model.Tint
import com.camg_apps.axfactor.databinding.FragmentMaterialListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


class InfoFormulaFragment : Fragment() {

    private var total: Double = 0.0
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

        //setHasOptionsMenu(true)

        Toast.makeText(requireContext(), formula!!.tints.toString(), Toast.LENGTH_SHORT).show()
       adapter = MaterialAdapter(formula!!.tints!! as ArrayList<Tint>, requireContext(), vmInfoFormula, formula!!.code_reference)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(requireContext())

        configureTotalTints()

        //changes listener for total tints
        binding.etLiters.addTextChangedListener {
            if (!it.toString().isNullOrBlank() && it.toString().toDouble() >= 0.1) {
                nuevoTotal = it.toString().toDouble()
                setNewList()
            }
        }



        setViewsData()
        return binding.root
    }

    private fun setNewList() {
        var tints = formula!!.tints
        if (tints != null) {
            for (tint in tints){
                var nuevoTint  = getNuevosGramos(tint.weight!!).toDouble()
                tint.weight = nuevoTint
            }
        }
        adapter.setData(tints as ArrayList<Tint>)
    }

    private fun getNuevosGramos(dato: Double) : String {
        Log.d("InfoFormulaFragment", "TOtal: $total, NuevoTotal: $nuevoTotal, dato: $dato" )
        return decimalFormatDouble((dato/total)*nuevoTotal, "0.0")
    }

    fun decimalFormatDouble(dato: Double, format: String): String{
        var simbolos = DecimalFormatSymbols()
        simbolos.decimalSeparator = '.'
        val decimalFormat = DecimalFormat(format)
        return decimalFormat.format(dato)
    }

    fun getTotal(){
        var nuevoTotal = 0
        for (tint in formula!!.tints!!){
            total += tint.weight!!
        }
    }


    private fun configureTotalTints() {
        //get total from wight tints
        for (tint in formula!!.tints!!){
            total += tint.weight!!
        }

        nuevoTotal = total

        binding.etLiters.setText(total.toString())

    }
/*
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

 */

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
        adapter.setData(formula!!.tints as ArrayList<Tint>)
    }

    private fun setNewMaterial(codigo: String, gramos: Double) {
        vmInfoFormula.setNewMaterial(Tint(name = codigo, weight = gramos), MyUtils.getUserUid(requireContext()), formula!!.code_reference!!)
    }

    private fun setViewsData() {
        formula?.let {
            binding.textViewCodigo.text = it.code_reference
            binding.textViewMarca.text = it.description
            binding.imageViewColor.setBackgroundColor(it.color!!.toInt())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}