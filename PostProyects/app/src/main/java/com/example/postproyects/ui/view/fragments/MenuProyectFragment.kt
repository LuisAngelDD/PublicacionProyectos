package com.example.postproyects.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.postproyects.R
import com.example.postproyects.data.model.ApiResponse
import com.example.postproyects.databinding.MenuProyectFragmentBinding
import com.example.postproyects.ui.viewmodel.*
import com.example.postproyects.utils.ConnectivityManager
import com.example.postproyects.utils.nav
import com.example.postproyects.utils.toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuProyectFragment : Fragment() {
    private val args : MenuProyectFragmentArgs by navArgs()
    private val cm : ConnectivityManager by activityViewModels()
    private val ldvm : LocalDataViewModel by activityViewModels()
    private val pvm : ProyectViewModel by viewModels()
    private val mvm : MoveViewModel by activityViewModels()
    private val svm : SocketViewModel by activityViewModels()
    private var _binding: MenuProyectFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = MenuProyectFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        svm.updateFollows()
        svm.updateLikes()
        pvm.findProyect(args.id)
        binding.btnReturn.setOnClickListener {
            pvm.exit()
            svm.exit()
            mvm.toMenu()
            nav(Navigation.findNavController(view), MenuProyectFragmentDirections.actionProyectFragmentToMenuFragment())
        }
        pvm.find_pr.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.nameProyect.text = it.nombre
                binding.descProyect.text = it.desc
                binding.author.text = it.author
                binding.statusProyect.text = it.estado
                binding.typeProyect.text = it.tipo
                if (cm.isNetworkAvailable.value==true){
                    pvm.findFollows(it.id)
                    pvm.findLikes(it.id)
                }
                when (ldvm.data.value) {
                    null,"" -> {}
                    else -> {
                        if (cm.isNetworkAvailable.value==true){
                            pvm.ifFollow(ldvm.data.value.toString(),it.id)
                            pvm.ifLike(ldvm.data.value.toString(),it.id)
                        }
                    }
                }
            }
        })
        pvm.find_follows.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.countFollows.text = it.mensaje
            }
        })
        pvm.find_likes.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.countLikes.text = it.mensaje
            }
        })
        pvm.if_like.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if (it.code){
                    binding.imgLike.setImageResource(R.drawable.ic_pulgares_hacia_arriba_dark)
                } else {
                    binding.imgLike.setImageResource(R.drawable.ic_pulgares_hacia_arriba)
                }
            }
        })
        pvm.if_follow.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if (it.code){
                    binding.imgFollow.setImageResource(R.drawable.ic_marcador_darck)
                } else {
                    binding.imgFollow.setImageResource(R.drawable.ic_marcador_ligth)
                }
            }
        })
        pvm.set_follow.observe(viewLifecycleOwner, Observer {
            if (it){
                svm.searchUpdatesFollows(pvm.find_pr.value!!.id)
                svm.updateDataProyects()
            }
        })
        svm.count_follows.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (pvm.find_pr.value!!.id == it.getString("codeProyecto")){
                    pvm.updateFollows(ApiResponse(true,it.getString("count")))
                }
            }
        })
        pvm.set_like.observe(viewLifecycleOwner, Observer {
            if (it){
                svm.searchUpdatesLikes(pvm.find_pr.value!!.id)
                svm.updateDataProyects()
            }
        })
        svm.count_likes.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (pvm.find_pr.value!!.id == it.getString("codeProyecto")){
                    pvm.updateLikes(ApiResponse(true,it.getString("count")))
                }
            }
        })
        binding.imgLike.setOnClickListener {
            when (ldvm.data.value) {
                null,"" -> {
                    toast(getString(R.string.msj_go_post_proyect))
                }
                else -> {
                    pvm.setLike(ldvm.data.value.toString(), pvm.find_pr.value!!.id)
                }
            }
        }
        binding.imgFollow.setOnClickListener {
            when (ldvm.data.value) {
                null,"" -> {
                    toast(getString(R.string.msj_go_post_proyect))
                }
                else -> {
                    pvm.setFollow(ldvm.data.value.toString(),pvm.find_pr.value!!.id)
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        mvm.stayOut()
        _binding = null
    }
}