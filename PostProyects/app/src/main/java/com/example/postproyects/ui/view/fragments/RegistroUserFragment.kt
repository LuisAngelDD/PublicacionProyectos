package com.example.postproyects.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.util.PatternsCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.postproyects.R
import com.example.postproyects.databinding.RegistroUserFragmentBinding
import com.example.postproyects.ui.viewmodel.acceso_registro.RegistroUserViewModel
import com.example.postproyects.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistroUserFragment : Fragment() {
    private val cm : ConnectivityManager by activityViewModels()
    private lateinit var ruvm : RegistroUserViewModel
    private var _binding: RegistroUserFragmentBinding? = null
    private val binding get() = _binding!!
    private var color: Int = R.color.weak
    private val a = arrayListOf<Int>()
    private var status:Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegistroUserFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ruvm = ViewModelProvider(this)[RegistroUserViewModel::class.java]
        val passwordStrengthCalculator = PasswordStrengthCalculator()
        a.addAll(listOf(0,0,0))
        binding.pass.addTextChangedListener(passwordStrengthCalculator)
        // Observe
        passwordStrengthCalculator.strengthLevel.observe(viewLifecycleOwner, Observer {strengthLevel ->
            displayStrengthLevel(strengthLevel)
            status = strengthLevel == StrengthLevel.ACEPTABLE || strengthLevel == StrengthLevel.MEDIO || strengthLevel == StrengthLevel.FUERTE
        })
        passwordStrengthCalculator.strengthColor.observe(viewLifecycleOwner, Observer {strengthColor ->
            color = strengthColor
        })
        passwordStrengthCalculator.lowerCase.observe(viewLifecycleOwner, Observer {value ->
            displayPasswordSuggestions(value, binding.lowerCaseImg, binding.lowerCaseTxt)
        })
        passwordStrengthCalculator.upperCase.observe(viewLifecycleOwner, Observer {value ->
            displayPasswordSuggestions(value, binding.upperCaseImg, binding.upperCaseTxt)
        })
        passwordStrengthCalculator.digit.observe(viewLifecycleOwner, Observer {value ->
            displayPasswordSuggestions(value, binding.digitImg, binding.digitTxt)
        })
        passwordStrengthCalculator.specialChar.observe(viewLifecycleOwner, Observer {value ->
            displayPasswordSuggestions(value, binding.specialCharImg, binding.specialCharTxt)
        })
        passwordStrengthCalculator.longCharacters.observe(viewLifecycleOwner, Observer {value ->
            displayPasswordSuggestions(value, binding.longCharImg, binding.longCharTxt)
        })
        binding.correo.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout1.error = null
        }
        binding.pass.doOnTextChanged { text, start, before, count ->
            if (text?.length == 0){
                binding.section0.isVisible = false
                binding.section1.isVisible = false
                binding.section2.isVisible = false
                binding.section3.isVisible = false
                binding.section4.isVisible = false
                binding.pswSafe.isVisible = false
                binding.strengthLevelIndicator.isVisible = false
            }
            binding.textInputLayout2.error = null
        }
        binding.repeatPass.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout3.error = null
        }
        binding.btnSignUp.setOnClickListener {
            binding.textInputLayout1.error = null
            binding.textInputLayout2.error = null
            binding.textInputLayout3.error = null
            if (!binding.correo.text.toString().isNullOrEmpty()) {
                if (!PatternsCompat.EMAIL_ADDRESS.matcher(binding.correo.text.toString()).matches()){
                    a[0] = 1
                    binding.textInputLayout1.error = "Error"
                }
            } else {
                a[0] = 1
                binding.textInputLayout1.error = "Error"
            }
            if (!binding.pass.text.toString().isNullOrEmpty() || !binding.repeatPass.text.toString().isNullOrEmpty()) {
                if (binding.pass.text.toString().isNullOrEmpty() && !status) {
                    a[1] = 1
                    binding.textInputLayout2.error = "Campo vacio"
                } else if (binding.repeatPass.text.toString().isNullOrEmpty()) {
                    a[2] = 1
                    binding.textInputLayout3.error = "Campo vacio"
                } else if (!binding.repeatPass.text.toString().equals(binding.pass.text.toString())){
                    a[2] = 1
                    binding.textInputLayout3.error = "Error la contraseña no coincide"
                }
            } else {
                a[1] = 1
                a[2] = 1
                binding.textInputLayout2.error = "Error"
                binding.textInputLayout3.error = "Error"
            }
            if (a[0] == 0 && a[1] == 0 && a[2] == 0){
                if (cm.isNetworkAvailable.value == true){
                    ruvm.register(binding.correo.text.toString(),binding.pass.text.toString())
                } else {
                    toast("No hay conexion a internet")
                }
            }else{
                toast("Hay campos incorrectos")
            }
            a[0] = 0
            a[1] = 0
            a[2] = 0
        }
        binding.btnLabelSignIn.setOnClickListener {
            nav(Navigation.findNavController(view), RegistroUserFragmentDirections.actionRegistroUserFragmentToAccesoUserFragment())
        }
        ruvm.isLoading.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = it
        })
        ruvm.message.observe(viewLifecycleOwner, Observer {
            toast(it)
        })
        ruvm.action.observe(viewLifecycleOwner, Observer {
            nav(Navigation.findNavController(view), RegistroUserFragmentDirections.actionRegistroUserFragmentToAccesoUserFragment())
        })
    }
    private fun displayPasswordSuggestions(value: Int, imageView: ImageView, textView: TextView) {
        if(value == 1){
            imageView.setColorFilter(color(R.color.bulletproof))
            textView.setTextColor(color(R.color.bulletproof))
        }else{
            imageView.setColorFilter(color(R.color.darkGray))
            textView.setTextColor(color(R.color.darkGray))
        }
    }
    private fun displayStrengthLevel(strengthLevel: StrengthLevel) {
        binding.section0.isVisible = true
        binding.section1.isVisible = true
        binding.section2.isVisible = true
        binding.section3.isVisible = true
        binding.section4.isVisible = true
        binding.pswSafe.isVisible = true
        binding.strengthLevelIndicator.isVisible = true
        binding.pswSafe.text = strengthLevel.toString()
        binding.pswSafe.setTextColor(ContextCompat.getColor(requireContext(), color))
        binding.strengthLevelIndicator.setBackgroundColor(ContextCompat.getColor(requireContext(), color))
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}