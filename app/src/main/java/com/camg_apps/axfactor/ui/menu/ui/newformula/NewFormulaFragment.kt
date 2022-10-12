package com.camg_apps.axfactor.ui.menu.ui.newformula

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.common.MyUtils
import com.camg_apps.axfactor.data.model.Formula
import com.camg_apps.axfactor.data.model.Material
import com.camg_apps.axfactor.databinding.FragmentNewFormulaBinding
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerClickListener
import com.flask.colorpicker.builder.ColorPickerDialogBuilder


class NewFormulaFragment : Fragment() {


    private var _binding: FragmentNewFormulaBinding? = null
    val binding get() = _binding!!
    val vmNewFormula: NewFormulaViewModel by viewModels()
    private var armadoras = ArrayList<String>()
    private var lineas = ArrayList<String>()
    private var color: Int = Color.BLACK

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentNewFormulaBinding.inflate(inflater, container, false)

        initObservers()
        initEvents()

        if(armadoras.isEmpty()){
            vmNewFormula.getArmadoras()
        }
        if(lineas.isEmpty()){
            vmNewFormula.getLineas()
        }

        //SET NEW FORMULA
        binding.buttonAceptar.setOnClickListener {
          val userUid =  MyUtils.getUserUid(requireContext())
          val formula =  getNewFormula()
            val codigo = formula.codigo
            formula.codigo = null
            vmNewFormula.setNewFormula(formula, userUid, codigo!!)
            findNavController().popBackStack()
        }

        return  binding.root
    }

    private fun getNewFormula(): Formula {
        val codigo = binding.textInputCodigo.editText!!.text.toString()
        val linea = binding.spinnerLinea.editText!!.text.toString()
        val armadora = binding.spinnerArmadora.editText!!.text.toString()

        return Formula(codigo, armadora, linea, color.toLong(), listOf<Material>())
    }

    private fun initObservers() {
        vmNewFormula.armadorasLiveData.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                armadoras.clear()
                it.forEach { marca ->
                    armadoras.add(marca.name!!)
                }
                setUpSpinnerArmadora()
            }
        })

        vmNewFormula.lineasLiveData.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                lineas.clear()
                it.forEach { linea ->
                    lineas.add(linea.name!!)
                }
                setUpSpinnerLinea()
            }
        })


    }

    private fun setUpSpinnerLinea() {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, lineas)
        (binding.spinnerLinea.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun initEvents() {
        binding.textViewSelectColor.setOnClickListener {
            initColorPicker()
        }
    }

    private fun setUpSpinnerArmadora() {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, armadoras)
        (binding.spinnerArmadora.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun initColorPicker() {
        ColorPickerDialogBuilder
            .with(context)
            .setTitle(getString(R.string.fg_newformula_seleccionar_color))
            .initialColor(Color.WHITE)
            .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
            .density(12)
            .setPositiveButton("Aceptar",
                    ColorPickerClickListener { dialog, selectedColor, allColors ->
                        changeBackgroundColor(selectedColor)
                        Log.d("COLOR", selectedColor.toString())
                    })
            .setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .build()
            .show()
    }

    private fun changeBackgroundColor(selectedColor: Int) {
        binding.itemSelectColor.setBackgroundColor(selectedColor)
        color = selectedColor
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}