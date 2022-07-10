package com.example.postproyects.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.postproyects.data.model.proyects.Pr

class MyDiffUtil (
    private val oldList:List<Pr>,
    private val newList:List<Pr>
    ) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id ->{ false }
            oldList[oldItemPosition].nombre != newList[newItemPosition].nombre ->{ false }
            oldList[oldItemPosition].author != newList[newItemPosition].author ->{ false }
            oldList[oldItemPosition].desc != newList[newItemPosition].desc ->{ false }
            oldList[oldItemPosition].tipo != newList[newItemPosition].tipo ->{ false }
            oldList[oldItemPosition].estado != newList[newItemPosition].estado ->{ false }
            else -> true
        }
    }
}