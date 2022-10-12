package com.camg_apps.axfactor.ui.menu.ui.inventario

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.databinding.FragmentInventListBinding

class InventarioFragment : Fragment() {

    private var _binding: FragmentInventListBinding? = null
    val binding get() = _binding!!
    lateinit var adapter: InventarioAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentInventListBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        adapter = InventarioAdapter(listOf("assf", "afa"))
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(requireContext())



        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_inventario, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_new_inven -> {
                findNavController().navigate(R.id.action_nav_inventario_to_newInventarioFragment)
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