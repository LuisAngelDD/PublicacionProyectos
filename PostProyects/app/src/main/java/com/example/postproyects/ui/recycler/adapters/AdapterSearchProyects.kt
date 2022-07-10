package com.example.postproyects.ui.recycler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.postproyects.R
import com.example.postproyects.data.model.proyects.Pr
import com.example.postproyects.data.model.proyects.PrData
import com.example.postproyects.databinding.CardProyectsBinding

class AdapterSearchProyects (private val onClickListener:(PrData)->Unit):
    ListAdapter<PrData,AdapterSearchProyects.ViewHolder>(DiffUtilCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layooutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layooutInflater.inflate(R.layout.card_proyects,parent,false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPr = getItem(position)
        holder.bind(dataPr)
        holder.itemView.setOnClickListener {
            onClickListener(dataPr)
        }
    }
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val binding = CardProyectsBinding.bind(view)
        fun bind(proyecto: PrData) = with(binding){
            nombreproyecto.text =  proyecto.nombre
            status.text =  proyecto.estado
            type.text = proyecto.tipo
            countLikes.text = proyecto.likes
            countFollows.text = proyecto.follows
        }
    }
}
private object DiffUtilCallback:DiffUtil.ItemCallback<PrData>(){
    override fun areItemsTheSame(oldItem: PrData, newItem: PrData): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: PrData, newItem: PrData): Boolean = oldItem == newItem
}