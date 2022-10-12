package com.camg_apps.axfactor.ui.main.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.databinding.FragmentLoginBinding
import com.camg_apps.axfactor.ui.menu.MenuActivity


class LoginFragment : Fragment() {


    val vmLogin: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!
    private var email: String = ""
    private var password: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.buttonEntrar.setOnClickListener {
            getInputValues()
            if(email == "admin" && password == "admin"){
                val intent = Intent(requireContext(), MenuActivity::class.java)
                startActivity(intent)
        }}

        vmLogin.userLiveData.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                goToMenuActivity()
            }
        })

        return binding.root
    }

    /*private fun logIn() {
        vmLogin.login(email, password, requireActivity())
    }

     */

    private fun goToMenuActivity() {
        val intent = Intent(requireActivity(), MenuActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun getInputValues() {
      email = binding.InputUsuario.editText!!.text.toString()
        password = binding.InputContrasena.editText!!.text.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}