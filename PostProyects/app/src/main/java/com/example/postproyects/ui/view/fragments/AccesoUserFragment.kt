package com.example.postproyects.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.postproyects.databinding.AccesoUserFragmentBinding
import com.example.postproyects.ui.viewmodel.AuthViewModel
import com.example.postproyects.ui.viewmodel.LocalDataViewModel
import com.example.postproyects.ui.viewmodel.acceso_registro.AccesoViewModel
import com.example.postproyects.ui.viewmodel.CheckDataViewModel
import com.example.postproyects.ui.viewmodel.SocketViewModel
import com.example.postproyects.utils.ConnectivityManager
import com.example.postproyects.utils.hideKeyboard
import com.example.postproyects.utils.nav
import com.example.postproyects.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccesoUserFragment : Fragment() {
    private val cm : ConnectivityManager by activityViewModels()
    private val ldvm : LocalDataViewModel by activityViewModels()
    private val otpvm : AuthViewModel by activityViewModels()
    private val svm : SocketViewModel by activityViewModels()
    private lateinit var avm : AccesoViewModel
    private lateinit var cdvm : CheckDataViewModel
    private var _binding: AccesoUserFragmentBinding? = null
    private val binding get() = _binding!!
    private var flag :Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = AccesoUserFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        avm = ViewModelProvider(this)[AccesoViewModel::class.java]
        cdvm = ViewModelProvider(this)[CheckDataViewModel::class.java]
        binding.correo.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout.error = null
        }
        binding.pass.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout1.error = null
        }
        binding.btnSignIn.setOnClickListener {
            hideKeyboard()
            if (flag) {
                binding.textInputLayout.error = null
                binding.textInputLayout1.error = null
                flag = false
                if (binding.correo.text.toString() == "" || binding.pass.text.toString() == "") {
                    toast("Los campos no pueden estar vacíos")
                    if (binding.correo.text.toString() == "") binding.textInputLayout.error = "Ingrese un correo"
                    if (binding.pass.text.toString() == "") binding.textInputLayout1.error = "Ingrese la contraseña del correo"
                    flag = true
                } else {
                    if (cm.isNetworkAvailable.value == true) {
                        avm.login(binding.correo.text.toString(), binding.pass.text.toString())
                    } else {
                        toast("No hay conexion a internet")
                    }
                }
            }else {
                toast("Espere hasta que la operacion se complete")
            }
        }
        binding.invitado.setOnClickListener {
            svm.setConnection()
            nav(Navigation.findNavController(view), AccesoUserFragmentDirections.actionAccesoUserFragmentToMenuFragment())
        }
        avm.messageError.observe(viewLifecycleOwner, Observer {
            binding.textInputLayout.error = "Error"
            binding.textInputLayout1.error = "Error"
            toast(it)
        })
        avm.answer.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                 cdvm.checkData(avm.message.value.toString())
            }
        })
        avm.answerAuth.observe(viewLifecycleOwner,Observer{
            when (it) {
                true -> {
                    otpvm.sendCode(binding.correo.text.toString())
                }
                false->{}
            }
        })
        otpvm.answer.observe(viewLifecycleOwner,Observer{
            if (it == true){
                otpvm.reset()
                nav(Navigation.findNavController(view), AccesoUserFragmentDirections.actionAccesoUserFragmentToAuthFragment())
            }
        })
        cdvm.answerCheck.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.INVISIBLE
            flag = true
            if (it == 200) {
                ldvm.search()
                svm.setConnection()
                nav(Navigation.findNavController(view), AccesoUserFragmentDirections.actionAccesoUserFragmentToMenuFragment())
            }else if (it == 401){
                toast("Error al iniciar sesion")
            }else if (it == 404) {
                nav(Navigation.findNavController(view), AccesoUserFragmentDirections.actionAccesoUserFragmentToUploadDataFragment())
            }
        })
        binding.registrarse.setOnClickListener {
            nav(Navigation.findNavController(view), AccesoUserFragmentDirections.actionAccesoUserFragmentToRegistroUserFragment())
        }
        avm.pr.observe(viewLifecycleOwner, Observer {
            if (it == true){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                flag = true
            }
        })
        otpvm.pr.observe(viewLifecycleOwner, Observer {
            if (it == true){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                flag = true
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}