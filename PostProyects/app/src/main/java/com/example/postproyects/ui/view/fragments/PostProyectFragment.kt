package com.example.postproyects.ui.view.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.postproyects.databinding.PostProyectFragmentBinding
import com.example.postproyects.ui.viewmodel.LocalDataViewModel
import com.example.postproyects.ui.viewmodel.MoveViewModel
import com.example.postproyects.ui.viewmodel.PostProyectViewModel
import com.example.postproyects.ui.viewmodel.SocketViewModel
import com.example.postproyects.ui.viewmodel.menu.MenuMainViewModel
import com.example.postproyects.utils.ConnectivityManager
import com.example.postproyects.utils.hideKeyboard
import com.example.postproyects.utils.nav
import com.example.postproyects.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostProyectFragment : Fragment() {
    private val cm : ConnectivityManager by activityViewModels()
    private val mmvm : MenuMainViewModel by viewModels()
    private val ldvm : LocalDataViewModel by activityViewModels()
    private val svm : SocketViewModel by activityViewModels()
    private val mvm : MoveViewModel by activityViewModels()
    private lateinit var ppvm: PostProyectViewModel
    private var _binding: PostProyectFragmentBinding? = null
    private val binding get() = _binding!!
    private var type : String = ""
    private var items = arrayListOf<String>()
    private val a = arrayListOf<Int>()
    private var flag :Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = PostProyectFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ppvm = ViewModelProvider(this)[PostProyectViewModel::class.java]
        a.addAll(listOf(0,0,0))
        mmvm.loadType()
        mmvm.type.observe(viewLifecycleOwner, Observer {
            for (name in it) {
                items.add(name.tipo)
            }
            val itemAdapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, items)
            with(binding.typeProyect){
                setAdapter(itemAdapter)
                onItemClickListener = android.widget.AdapterView.OnItemClickListener { parent, view, position, id -> type=parent?.getItemAtPosition(position).toString() }
            }
        })
        binding.nameProyect.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout.error = null
        }
        binding.typeProyect.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout1.error = null
        }
        binding.descProyect.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout2.error = null
        }
        binding.imageView7.setOnClickListener {
            hideKeyboard()
        }
        binding.imageView8.setOnClickListener {
            hideKeyboard()
        }
        binding.btnPost.setOnClickListener {
            binding.textInputLayout.error = null
            binding.textInputLayout1.error = null
            binding.textInputLayout2.error = null
            hideKeyboard()
            if (flag) {
                flag = false
                if (binding.nameProyect.text.toString().isNullOrEmpty()){
                    binding.textInputLayout.error = "Ingrese un nombre"
                    a[0] = 1
                }
                if (type == ""){
                    binding.textInputLayout1.error = "Ingrese un tipo de proyecto"
                    a[1] = 1
                }
                if (binding.descProyect.text.toString().isNullOrEmpty()){
                    binding.textInputLayout2.error = "Ingrese una descrpcion"
                    a[2] = 1
                }
                if (a[0]==0&&a[1]==0&&a[2]==0){
                    if (cm.isNetworkAvailable.value == true){
                        ppvm.post(ldvm.data.value.toString(),binding.nameProyect.text.toString(),binding.descProyect.text.toString(),type)
                    } else {
                        toast("No hay conexion a internet")
                    }
                }else {
                    flag = true
                }
                a[0] = 0
                a[1] = 0
                a[2] = 0
            } else {
                toast("Espere hasta que la operacion se complete")
            }
        }
        ppvm.answerCheck.observe(viewLifecycleOwner, Observer {
            if (it != false){
                svm.newProyects()
                mvm.toMenu()
                nav(Navigation.findNavController(view), PostProyectFragmentDirections.actionPostProyectFragmentToMenuFragment())
            }else{
                toast("Ocurrio un erro al publicar el proyecto")
            }
        })
        binding.btnReturn.setOnClickListener {
            mvm.toMenu()
            nav(Navigation.findNavController(view), PostProyectFragmentDirections.actionPostProyectFragmentToMenuFragment())
        }
        ppvm.pr.observe(viewLifecycleOwner, Observer {
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
        mvm.stayOut()
        _binding = null
    }
}