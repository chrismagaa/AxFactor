package com.camg_apps.axfactor.ui.menu.ui.ui_admin.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.data.model.User
import com.camg_apps.axfactor.databinding.FragmentUserListBinding

class UserFragment : Fragment() {

    private var laboratorioName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            laboratorioName = it.getString(ARG_LABORATORIO).toString()
        }
    }
    private var _binding: FragmentUserListBinding? = null
    private val binding get () = _binding!!
    private lateinit var adapter: UserAdapter
    private var listUsers = listOf<User>()
    private val vmUsers: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)

        adapter = UserAdapter(listUsers)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(requireContext())

        vmUsers.usersLiveData.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                listUsers = it
                setDataAdapter()
            }
        })

        if(listUsers.isEmpty()){
            vmUsers.getUsers()
        }


        return binding.root
    }

    private fun setDataAdapter() {
        adapter.setData(listUsers)
    }

    companion object {
        const val ARG_LABORATORIO = "laboratorio_name"
    }
}