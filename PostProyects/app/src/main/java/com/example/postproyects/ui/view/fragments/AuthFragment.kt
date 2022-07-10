package com.example.postproyects.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.postproyects.databinding.AuthFragmentBinding
import com.example.postproyects.ui.viewmodel.AuthViewModel
import com.example.postproyects.ui.viewmodel.LocalDataViewModel
import com.example.postproyects.utils.nav
import com.example.postproyects.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private val otpvm : AuthViewModel by activityViewModels()
    private val sldvm : LocalDataViewModel by activityViewModels()
    private var flag = true
    private var _binding: AuthFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = AuthFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.number.doOnTextChanged { text, start, before, count ->
            if (!text.toString().trim().isNullOrEmpty()){
                binding.number1.requestFocus()
            }
        }
        binding.number1.doOnTextChanged { text, start, before, count ->
            if (!text.toString().trim().isNullOrEmpty()){
                binding.number2.requestFocus()
            }
        }
        binding.number2.doOnTextChanged { text, start, before, count ->
            if (!text.toString().trim().isNullOrEmpty()){
                binding.number3.requestFocus()
            }
        }
        binding.number3.doOnTextChanged { text, start, before, count ->
            if (!text.toString().trim().isNullOrEmpty()){
                binding.number4.requestFocus()
            }
        }
        binding.number4.doOnTextChanged { text, start, before, count ->
            if (!text.toString().trim().isNullOrEmpty()){
                binding.number5.requestFocus()
            }
        }
        binding.btnVerificar.setOnClickListener {
            if (flag){
                flag = false
                if (binding.number.text.toString() == "" || binding.number1.text.toString() == ""|| binding.number2.text.toString() == ""|| binding.number3.text.toString() == ""|| binding.number4.text.toString() == ""|| binding.number5.text.toString() == ""){
                    toast("Hay campos vacios")
                    flag = true
                }else{
                    val data = binding.number.text.toString()+binding.number1.text.toString()+binding.number2.text.toString()+binding.number3.text.toString()+binding.number4.text.toString()+binding.number5.text.toString()
                    otpvm.checkCode(data)
                }
            }else {
                toast("Espere hasta que la operacion se complete")
            }
        }
        otpvm.pr.observe(viewLifecycleOwner, Observer {
            if (it == true){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                flag = true
            }
        })
        otpvm.answerCheck.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    sldvm.search()
                    nav(Navigation.findNavController(view), AuthFragmentDirections.actionAuthFragmentToUploadDataFragment())
                }
                false -> {}
            }
        })
        otpvm.messageError.observe(viewLifecycleOwner,Observer{
            toast(it)
        })
        binding.reSendCode.setOnClickListener {
            otpvm.sendEmail()
        }
        binding.btnReturn.setOnClickListener {
            nav(Navigation.findNavController(view), AuthFragmentDirections.actionAuthFragmentToAccesoUserFragment())
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}