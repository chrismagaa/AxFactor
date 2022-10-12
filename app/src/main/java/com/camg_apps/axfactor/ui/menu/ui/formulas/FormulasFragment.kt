package com.camg_apps.axfactor.ui.menu.ui.formulas

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.data.model.Formula
import com.camg_apps.axfactor.databinding.FragmentFormulasListBinding


class FormulasFragment : Fragment() {

    private var _binding: FragmentFormulasListBinding? = null
    val binding get() = _binding!!
    lateinit var adapter: FormulaAdapter

    val vmFormulas: FormulasViewModel by viewModels()
    private var armadoras = ArrayList<String>()
    private var lineas = ArrayList<String>()
    private var formulas = ArrayList<Formula>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormulasListBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        adapter = FormulaAdapter(listOf(), findNavController())
        binding.list.adapter = adapter
        binding.list.layoutManager = GridLayoutManager(requireContext(), 2)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        vmFormulas.setReferenceFormulas(requireActivity())

        vmFormulas.formulasLiveData.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                formulas.clear()
                formulas = it as ArrayList<Formula>
                setDataAdapter()
            }
        })

        vmFormulas.armadorasLiveData.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                armadoras.clear()
                armadoras.add("Armadoras")
                it.forEach { marca ->
                    armadoras.add(marca.name!!)
                }
                setUpSpinnerArmadora()
            }
        })

        vmFormulas.lineasLiveData.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                lineas.clear()
                lineas.add("Lineas")
                it.forEach { linea ->
                    lineas.add(linea.name!!)
                }
                setUpSpinnerLinea()
            }
        })

        if(armadoras.isEmpty()){
            vmFormulas.getArmadoras()
        }
        if(lineas.isEmpty()){
            vmFormulas.getLineas()
        }
        if(formulas.isEmpty()){
            vmFormulas.getFormulas()
        }
    }

    private fun setDataAdapter() {
        adapter.setData(formulas)
    }

    private fun setUpSpinnerLinea() {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, lineas)
        (binding.spinnLinea.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setUpSpinnerArmadora() {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, armadoras)
        (binding.spinnArmadora.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_new_formula -> {
                findNavController().navigate(R.id.action_nav_formulas_to_newFormulaFragment)
            }
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}