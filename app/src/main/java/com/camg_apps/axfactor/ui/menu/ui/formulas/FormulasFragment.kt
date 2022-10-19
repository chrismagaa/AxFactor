package com.camg_apps.axfactor.ui.menu.ui.formulas

import android.os.Bundle
import android.util.Log
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
    private var formulas = ArrayList<Formula>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormulasListBinding.inflate(inflater, container, false)

        //setHasOptionsMenu(true)

        adapter = FormulaAdapter(listOf(), findNavController())
        binding.list.adapter = adapter
        binding.list.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.btnSearch.setOnClickListener {
            binding.tvSearchSometing.visibility = View.GONE
            binding.progress.visibility = View.VISIBLE

            vmFormulas.getReferenceFormulas()
        }

        vmFormulas.getReferenceFormulas()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        vmFormulas.setReferenceFormulas(requireActivity())

        vmFormulas.formulasLiveData.observe(viewLifecycleOwner, Observer {
            binding.progress.visibility = View.GONE
            if (!it.isNullOrEmpty()) {
                formulas.clear()
                formulas = it as ArrayList<Formula>
                Log.d("FormulasFragment", "Formulas: $formulas")
                setDataAdapter()
            }else{
                binding.tvSearchSometing.visibility = View.VISIBLE
            }
        })


        if(formulas.isEmpty()){
            vmFormulas.getReferenceFormulas()
        }
    }

    private fun setDataAdapter() {
        adapter.setData(formulas)
    }



/*
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

 */



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}