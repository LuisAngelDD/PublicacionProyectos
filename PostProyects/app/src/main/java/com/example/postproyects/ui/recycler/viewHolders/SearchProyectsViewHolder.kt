package com.example.postproyects.ui.recycler.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.postproyects.data.model.proyects.Pr
import com.example.postproyects.databinding.CardProyectsBinding

class SearchProyectsViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = CardProyectsBinding.bind(view)
    fun build(proyecto: Pr, onClickListener:(Pr)->Unit){
        binding.nombreproyecto.text =  proyecto.nombre
        binding.status.text =  proyecto.estado
        itemView.setOnClickListener{onClickListener(proyecto)}
    }
}