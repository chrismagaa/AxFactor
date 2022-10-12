package com.camg_apps.axfactor.ui.menu.ui.new_inventario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.data.model.Formula
import com.camg_apps.axfactor.data.model.Linea
import com.camg_apps.axfactor.data.model.Material
import com.camg_apps.axfactor.databinding.FragmentNewInventarioBinding
import java.security.CodeSigner

class NewInventarioFragment : Fragment() {

    private var _binding: FragmentNewInventarioBinding? = null
    val binding get() = _binding!!
    private val vmNewInventario: NewInventarioViewModel by viewModels()

    private var formulas = ArrayList<Formula>()
    private var filterFormulas = ArrayList<Formula>()
    private var strLineas = ArrayList<String>()
    private var strCodigos = ArrayList<String>()
    private var lineaSelected="Lineas"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentNewInventarioBinding.inflate(inflater, container, false)


        initSpinnerLinea()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        vmNewInventario.lineasLiveData.observe(viewLifecycleOwner, Observer {
            it.let {
                strLineas.add("Lineas")
                it.forEach {
                    strLineas.add(it.name!!)
                }
            }
        })

        if(strLineas.isNullOrEmpty()){
            vmNewInventario.getLineas()
        }

        vmNewInventario.formulasLiveData.observe(viewLifecycleOwner, Observer {
            it.let {formulaList ->
                formulas = formulaList as ArrayList<Formula>
            }
        })
        if(formulas.isNullOrEmpty()){
            vmNewInventario.getFormulas()
        }


    }

    private fun initSpinnerLinea() {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, strLineas)
        val autocomplete = (binding.spinnerLinea.editText as? AutoCompleteTextView)
        autocomplete?.setAdapter(adapter)
        autocomplete!!.setOnItemClickListener { parent, view, position, id ->
            lineaSelected = strLineas[position]

            filterFormulas = vmNewInventario.getFormulasWithLinea(lineaSelected)
            establecerCodigosString()
            setAdapterCodigos()
        }
    }

    private fun setAdapterCodigos() {
        val adapterCodigos = ArrayAdapter(requireContext(), R.layout.list_item, strCodigos)
        (binding.spinnerCodigo.editText as? AutoCompleteTextView)?.setAdapter(adapterCodigos)
    }

    private fun establecerCodigosString() {
        strCodigos.clear()
        filterFormulas.forEach {
            strCodigos.add(it.codigo!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}