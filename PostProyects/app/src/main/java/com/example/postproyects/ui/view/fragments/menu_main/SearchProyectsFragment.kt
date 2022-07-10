package com.example.postproyects.ui.view.fragments.menu_main

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.postproyects.R
import com.example.postproyects.data.model.proyects.*
import com.example.postproyects.databinding.SearchProyectsFragmentBinding
import com.example.postproyects.ui.recycler.adapters.AdapterSearchProyects
import com.example.postproyects.ui.viewmodel.MoveViewModel
import com.example.postproyects.ui.viewmodel.PostProyectViewModel
import com.example.postproyects.ui.viewmodel.SocketViewModel
import com.example.postproyects.ui.viewmodel.menu.MenuMainViewModel
import com.example.postproyects.utils.ConnectivityManager
import com.example.postproyects.utils.color
import com.example.postproyects.utils.hideKeyboard
import com.example.postproyects.utils.toast
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchProyectsFragment : Fragment(), SearchView.OnQueryTextListener {
    private val cm : ConnectivityManager by activityViewModels()
    private val mvm : MoveViewModel by activityViewModels()
    private val mmvm : MenuMainViewModel by viewModels()
    private val svm : SocketViewModel by activityViewModels()
    private var _binding: SearchProyectsFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { AdapterSearchProyects { onItemSelected(it) } }
    private var state = false
    private var type = "All"
    private var status = "All"
    private var name = ""
    private var flag = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchProyectsFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.svProyectos.setOnQueryTextListener(this)
        binding.rvProyects.isVisible = false
        binding.errorFindData.isVisible = false
        binding.errorLoadData.isVisible = false
        initRecyclerView()
        if (cm.isNetworkAvailable.value == true){
            mmvm.loadType()
            mmvm.loadStatus()
            search()
        } else {
            search()
        }
        cm.isNetworkAvailable.observe(viewLifecycleOwner, Observer{
            if (it){
                if (binding.errorLoadData.isVisible){
                    mmvm.loadType()
                    mmvm.loadStatus()
                    if (!name.isNullOrEmpty()){
                        search()
                    }else {
                        search(name)
                    }
                }
            }else {
            }
        })
        binding.swipProyects.setOnRefreshListener {
            if (cm.isNetworkAvailable.value == true) {
                if (!name.isNullOrEmpty()){
                    search()
                }else {
                    search(name)
                }
            } else {
                search()
            }
            binding.swipProyects.isRefreshing = false
        }
        mmvm.pr_card.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()){
                adapter.submitList(it)
            }else {
                adapter.submitList(emptyList<PrData>())
            }
        })
        mmvm.answer.observe(viewLifecycleOwner, Observer {
            if (it == true){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                flag = true
            }
        })
        mmvm.ui.observe(viewLifecycleOwner, Observer {
            when (it) {
                0 -> { binding.errorFindData.isVisible = false
                    binding.errorLoadData.isVisible = false
                    binding.rvProyects.isVisible = true}
                1 -> { binding.errorLoadData.isVisible = false
                    binding.rvProyects.isVisible = false
                    binding.errorFindData.isVisible = true}
                2 -> { binding.rvProyects.isVisible = false
                    binding.errorFindData.isVisible = false
                    binding.errorLoadData.isVisible = true}
            }
        })
        mmvm.type.observe(viewLifecycleOwner, Observer {
            binding.chipGroupType.removeAllViews()
            addFirst(1)
            for (name in it) {
                addChipTypes(name)
            }
        })
        mmvm.status.observe(viewLifecycleOwner, Observer {
            binding.chipGroupStatus.removeAllViews()
            addFirst(2)
            for (name in it) {
                addChipStatus(name)
            }
        })
        binding.btnFilters.setOnClickListener {
            if (state){
                binding.dataFilters.isVisible = false
                binding.btnFilters.setImageResource(R.drawable.ic_angulo_pequeno_hacia_abajo)
                state = false
            }else{
                binding.dataFilters.isVisible = true
                binding.btnFilters.setImageResource(R.drawable.ic_angulo_pequeno_hacia_arriba)
                state = true
            }
        }
        svm.notificationProyects()
        svm.txt.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.newProyect.visibility = View.VISIBLE
                binding.newProyect.text = it
                binding.newProyect.setBackgroundColor(color(R.color.purple_200))
                Handler().postDelayed({
                    binding.newProyect.visibility = View.GONE
                    svm.exit()
                },1800)
            }
        })
        svm.updateProyect()
        svm.opt.observe(viewLifecycleOwner, Observer {
            if (it){
                mmvm.loadFollows()
                svm.exit()
            }
        })
    }
    private fun search(){
        mmvm.searchProyects(status,type,"")
    }
    private fun search(name:String){
        mmvm.searchProyects(status,type,name)
    }
    private fun addChipTypes(types: TypeProyects){
        val chip = Chip(context)
        chip.text = types.tipo
        chip.isClickable = true
        chip.isCheckable = true
        chip.setOnClickListener {
            type = types.tipo
            search()
        }
        binding.chipGroupType.addView(chip)
    }private fun addChipStatus(statusP: StatusProyects){
        val chip = Chip(context)
        chip.text = statusP.status
        chip.isClickable = true
        chip.isCheckable = true
        chip.setOnClickListener {
            status = statusP.status
            search()
        }
        binding.chipGroupStatus.addView(chip)
    }
    private fun addFirst(how:Int){
        val chip = Chip(context)
        chip.text = "All"
        chip.isClickable = true
        chip.isCheckable = true
        chip.id = View.generateViewId()
        chip.setOnClickListener {
            if (how == 1){
                type = "All"
                search()
            } else {
                status = "All"
                search()
            }
        }
        if (how == 1){
            binding.chipGroupType.addView(chip)
            binding.chipGroupType.check(chip.id)
        } else {
            binding.chipGroupStatus.addView(chip)
            binding.chipGroupStatus.check(chip.id)
        }
    }
    private fun initRecyclerView(){
        val manager = GridLayoutManager(context,2)
        binding.rvProyects.layoutManager = manager
        binding.rvProyects.adapter = adapter
        adapter.submitList(emptyList<PrData>())
    }
    private fun onItemSelected(dataPr: PrData){
        mvm.viewProyect(dataPr.id)
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            name = query
            search(query)
        }
        hideKeyboard()
        return true
    }
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.equals("")){
            if (name.isNullOrEmpty()){
            }else{
                name = ""
                search()
                hideKeyboard()
            }
        }
        return true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        adapter.submitList(emptyList<PrData>())
        mmvm.exit()
        _binding = null
    }
}