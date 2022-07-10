package com.example.postproyects.ui.recycler.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.postproyects.data.model.ViewPager
import com.example.postproyects.databinding.ItemContainerBinding

class ViewHolderViewPager (view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemContainerBinding.bind(view)
    fun build(viewPager: ViewPager){
        binding.img.setImageResource(viewPager.icon)
        binding.title.text = viewPager.titulo
        binding.desc.text = viewPager.descripcion
    }
}