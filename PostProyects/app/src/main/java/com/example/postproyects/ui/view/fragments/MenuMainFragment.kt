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
import com.example.postproyects.R
import com.example.postproyects.databinding.MenuMainFragmentBinding
import com.example.postproyects.ui.viewmodel.DataUserViewModel
import com.example.postproyects.ui.viewmodel.MoveViewModel
import com.example.postproyects.ui.viewmodel.LocalDataViewModel
import com.example.postproyects.ui.viewmodel.SocketViewModel
import com.example.postproyects.utils.ConnectivityManager
import com.example.postproyects.utils.isNull
import com.example.postproyects.utils.nav
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuMainFragment : Fragment(R.layout.menu_main_fragment) {
    private val cm : ConnectivityManager by activityViewModels()
    private val mvm : MoveViewModel by activityViewModels()
    private val ldvm : LocalDataViewModel by activityViewModels()
    private val svm : SocketViewModel by activityViewModels()
    private val duvm : DataUserViewModel by activityViewModels()
    private var _binding: MenuMainFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = MenuMainFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvm.stay(view.findViewById<BottomNavigationView>(R.id.nav_menu_view_activity_menu),R.id.nav_host_fragment_activity_menu)
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
                        binding.imageView.load(it.url)
                    }
                    binding.userName.text = it.nombre
                }
            }
        })
        binding.imageView.setOnClickListener {
            if (!duvm.dataUser.value.isNull()){
                nav(Navigation.findNavController(view), MenuMainFragmentDirections.actionMenuFragmentToDataUserFragment())
            }else{
                svm.closeConnection()
                nav(Navigation.findNavController(view), MenuMainFragmentDirections.actionMenuFragmentToAccesoUserFragment())
            }
        }
        mvm.postProyect.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                nav(Navigation.findNavController(view), MenuMainFragmentDirections.actionMenuFragmentToPostProyectFragment())
            }
        })
        mvm.view_proyect.observe(viewLifecycleOwner, Observer {
            when (it) {
                null, "" -> {}
                else ->{
                    nav(Navigation.findNavController(view), MenuMainFragmentDirections.actionMenuFragmentToProyectFragment(it))
                }
            }
        })
        mvm.edit_proyect.observe(viewLifecycleOwner, Observer {
            when (it) {
                null, "" -> {}
                else ->{
                    nav(Navigation.findNavController(view), MenuMainFragmentDirections.actionMenuFragmentToUpdateDataPostFragment(it))
                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        mvm.stayOut()
        _binding = null
    }
}