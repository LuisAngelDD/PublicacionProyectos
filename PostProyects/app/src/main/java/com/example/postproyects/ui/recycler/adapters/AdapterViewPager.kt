package com.example.postproyects.ui.recycler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.postproyects.R
import com.example.postproyects.data.model.ViewPager
import com.example.postproyects.ui.recycler.viewHolders.ViewHolderViewPager

class AdapterViewPager (private val type:List<ViewPager>):
    RecyclerView.Adapter<ViewHolderViewPager>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderViewPager {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ViewHolderViewPager(layoutInflater.inflate(R.layout.item_container,parent,false))
    }
    override fun onBindViewHolder(holder: ViewHolderViewPager, position: Int) {
        val item = type[position]
        holder.build(item)
    }
    override fun getItemCount(): Int = type.size
}