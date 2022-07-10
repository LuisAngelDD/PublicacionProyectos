package com.example.postproyects.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import coil.load
import com.example.postproyects.databinding.DataUserFragmentBinding
import com.example.postproyects.ui.viewmodel.DataUserViewModel
import com.example.postproyects.ui.viewmodel.LocalDataViewModel
import com.example.postproyects.ui.viewmodel.SocketViewModel
import com.example.postproyects.utils.ConnectivityManager
import com.example.postproyects.utils.nav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DataUserFragment : Fragment() {
    private val cm : ConnectivityManager by activityViewModels()
    private val ldvm : LocalDataViewModel by activityViewModels()
    private val duvm : DataUserViewModel by activityViewModels()
    private val svm : SocketViewModel by activityViewModels()
    private var _binding: DataUserFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataUserFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ldvm.data.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (cm.isNetworkAvailable.value == true){
                    duvm.getData(it)
                }
            }
        })
        duvm.dataUser.observe(viewLifecycleOwner, Observer {
            when {
                it != null -> {
                    if (it.url != null){
                        binding.imgUser.load(it.url)
                    }
                    binding.txtNombre.text = it.nombre
                    binding.txtContact.text = it.email
                    binding.txtOcupation.text = it.ocupacion
                    binding.txtAbout.text = it.about
                }
            }
        })
        binding.btnReturn.setOnClickListener {
            nav(Navigation.findNavController(view), DataUserFragmentDirections.actionDataUserFragmentToMenuFragment())
        }
        binding.getOut.setOnClickListener {
            ldvm.delet()
            duvm.cleanData()
            svm.closeConnection()
            nav(Navigation.findNavController(view), DataUserFragmentDirections.actionDataUserFragmentToAccesoUserFragment())
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}