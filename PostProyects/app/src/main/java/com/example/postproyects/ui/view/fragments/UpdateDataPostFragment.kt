package com.example.postproyects.ui.view.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.postproyects.R
import com.example.postproyects.databinding.PostProyectFragmentBinding
import com.example.postproyects.databinding.UpdateDataPostFragmentBinding
import com.example.postproyects.ui.viewmodel.*
import com.example.postproyects.ui.viewmodel.menu.MenuMainViewModel
import com.example.postproyects.utils.ConnectivityManager
import com.example.postproyects.utils.hideKeyboard
import com.example.postproyects.utils.nav
import com.example.postproyects.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateDataPostFragment : Fragment() {
    private val args : UpdateDataPostFragmentArgs by navArgs()
    private val cm : ConnectivityManager by activityViewModels()
    private val pvm : ProyectViewModel by viewModels()
    private val mvm : MoveViewModel by activityViewModels()
    private val svm : SocketViewModel by activityViewModels()
    private val mmvm : MenuMainViewModel by viewModels()
    private lateinit var ppvm: PostProyectViewModel
    private var _binding: UpdateDataPostFragmentBinding? = null
    private val binding get() = _binding!!
    private var status : String = ""
    private var items = arrayListOf<String>()
    private val a = arrayListOf<Int>()
    private var flag :Boolean = true
    private var b = 0
    private var code = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = UpdateDataPostFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ppvm = ViewModelProvider(this)[PostProyectViewModel::class.java]
        pvm.findProyect(args.cod)
        a.addAll(listOf(0,0,0))
        pvm.find_pr.observe(viewLifecycleOwner, Observer {
            if (it != null){
                code = it.id
                binding.nameProyect.setText(it.nombre)
                binding.descProyect.setText(it.desc)
                status = it.estado
                mmvm.loadStatus()
            }
        })
        mmvm.status.observe(viewLifecycleOwner, Observer {
            var i = 0
            for (name in it) {
                items.add(name.status)
                if (status == name.status) b = i
                i++
            }
            val itemAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, items)
            with(binding.stateProyect){
                setText(itemAdapter.getItem(b),false)
                setAdapter(itemAdapter)
                onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> status=parent?.getItemAtPosition(position).toString() }
            }
        })
        binding.nameProyect.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout.error = null
        }
        binding.stateProyect.doOnTextChanged { text, start, before, count ->
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
                if (status == ""){
                    binding.textInputLayout1.error = "Ingrese un tipo de proyecto"
                    a[1] = 1
                }
                if (binding.descProyect.text.toString().isNullOrEmpty()){
                    binding.textInputLayout2.error = "Ingrese una descrpcion"
                    a[2] = 1
                }
                if (a[0]==0&&a[1]==0&&a[2]==0){
                    if (cm.isNetworkAvailable.value == true){
                        ppvm.put(code,binding.nameProyect.text.toString(),binding.descProyect.text.toString(),status)
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
                svm.updateDataProyects()
                mvm.toMenu()
                nav(Navigation.findNavController(view), UpdateDataPostFragmentDirections.actionUpdateDataPostFragmentToMenuFragment())
            }else{
                toast("Ocurrio un erro al actualizar el proyecto")
            }
        })
        binding.btnReturn.setOnClickListener {
            pvm.exit()
            svm.exit()
            mvm.toMenu()
            nav(Navigation.findNavController(view), UpdateDataPostFragmentDirections.actionUpdateDataPostFragmentToMenuFragment())
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