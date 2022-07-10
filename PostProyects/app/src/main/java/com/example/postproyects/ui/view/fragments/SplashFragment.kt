package com.example.postproyects.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.postproyects.databinding.SplashFragmentBinding
import com.example.postproyects.ui.viewmodel.LocalDataViewModel
import com.example.postproyects.ui.viewmodel.CheckDataViewModel
import com.example.postproyects.ui.viewmodel.SocketViewModel
import com.example.postproyects.utils.*
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class SplashFragment : Fragment() {
    private val cm : ConnectivityManager by activityViewModels()
    private val ldvm : LocalDataViewModel by activityViewModels()
    private val svm : SocketViewModel by activityViewModels()
    private lateinit var cdvm : CheckDataViewModel
    private var _binding: SplashFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = SplashFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cdvm = ViewModelProvider(this)[CheckDataViewModel::class.java]
        ldvm.search()
        ldvm.pr.observe(viewLifecycleOwner, Observer {
            if (it == true){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        cdvm.pr.observe(viewLifecycleOwner, Observer {
            if (it == false){
                binding.progressBar.visibility = View.GONE
            }
        })
        ldvm.answer.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    when (cm.isNetworkAvailable.value) {
                        true -> { cdvm.checkData(ldvm.data.value.toString()) }
                        else -> {}
                    }
                }
                false -> { nav(Navigation.findNavController(view), SplashFragmentDirections.actionSplashFragmentToAccesoUserFragment()) }
            }
        })
        cdvm.answerCheck.observe(viewLifecycleOwner, Observer {
            when (it) {
                200 -> {
                    svm.setConnection()
                    nav(Navigation.findNavController(view), SplashFragmentDirections.actionSplashFragmentToMenuFragment())}
                400,401 -> {ldvm.delet()
                    nav(Navigation.findNavController(view), SplashFragmentDirections.actionSplashFragmentToAccesoUserFragment()) }
                404 -> { nav(Navigation.findNavController(view), SplashFragmentDirections.actionSplashFragmentToUploadDataFragment()) }
                503 -> {}
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}