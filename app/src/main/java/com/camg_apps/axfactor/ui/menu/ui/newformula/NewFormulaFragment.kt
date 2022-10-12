package com.camg_apps.axfactor.ui.menu.ui.newformula

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.common.MyUtils
import com.camg_apps.axfactor.data.model.Formula
import com.camg_apps.axfactor.data.model.Material
import com.camg_apps.axfactor.databinding.FragmentNewFormulaBinding
import com.camg_apps.axfactor.ui.menu.ui.infoformula.MaterialAdapter
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerClickListener
import com.flask.colorpicker.builder.ColorPickerDialogBuilder


class NewFormulaFragment : Fragment() {


    private var _binding: FragmentNewFormulaBinding? = null
    val binding get() = _binding!!
    val vmNewFormula: NewFormulaViewModel by viewModels()
    private var color: Int = Color.BLACK

    private var materiales: ArrayList<Material> = ArrayList()
    lateinit var adapter: MaterialNewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentNewFormulaBinding.inflate(inflater, container, false)

        initEvents()

        setHasOptionsMenu(true)

        adapter = MaterialNewAdapter()
        binding.listNewTint.adapter = adapter
        binding.listNewTint.layoutManager = LinearLayoutManager(requireContext())

        vmNewFormula.textResult.observe(viewLifecycleOwner, Observer {
            if(it != null || it != ""){
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }

        })

        binding.btnAddTint.setOnClickListener {
            addTint()
        }

        return  binding.root
    }

    private fun addTint() {
        materiales.add(Material("",0.0))
        adapter.setData(materiales)
    }

    private fun getNewFormula(): Formula {
        val code_reference = binding.inputCodeReference.editText!!.text.toString()
        val description = binding.inputDescription.editText!!.text.toString()

        return Formula(code_reference, description, color.toLong(), listOf<Material>())
    }




    private fun initEvents() {
        binding.itemSelectColor.setOnClickListener {
            initColorPicker()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_new_formula, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_new_formula -> {
                //Create new formula
                val listMateriales = materiales.filter { it.gramos != 0.0 || it.gramos != null }

                val formula = Formula(binding.inputCodeReference.editText!!.text.toString(),
                    binding.inputDescription.editText!!.text.toString(),
                    color.toLong(),
                    listMateriales)

                vmNewFormula.setNewFormula(formula, formula.code_reference!!)
            }

        }
        return super.onOptionsItemSelected(item)
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