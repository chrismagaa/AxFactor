package com.camg_apps.axfactor.ui.menu.ui.ui_admin.laboratorios

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.camg_apps.axfactor.databinding.FragmentLaboratorioListBinding


class LaboratorioFragment : Fragment() {

    private val vmLaboratorio: LaboratorioViewModel by viewModels()
    private var listLaboratorio: List<String> = listOf()

    private var _binding: FragmentLaboratorioListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: LaboratorioAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentLaboratorioListBinding.inflate(inflater, container, false)

        adapter = LaboratorioAdapter(listLaboratorio, findNavController())
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(requireContext())

        //Init Observer Laboratorios
        vmLaboratorio.laboratoriosLiveData.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                listLaboratorio = it
                setValuesAdapter()
            }
        })

        if(listLaboratorio.isEmpty()){
            vmLaboratorio.getLaboratorios()
        }

        return binding.root
    }

    private fun setValuesAdapter() {
        adapter.setValues(listLaboratorio)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}