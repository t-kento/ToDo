package com.example.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list.view.*

class CustomAdapter (private val context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<ListObject>()
    private val items2=mutableListOf<ListObject>()

    var callback: CustomAdapterCallback? = null
    var done:CustomAdapterDone?=null

    fun refresh(list:List<ListObject>){
        items.apply{
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount(): Int = items.size

    private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = items[position]
        holder.apply {
            textView1
            deleteButton.setOnClickListener {
                callback?.onClick(data)
            }
            checkedButton.setOnClickListener {
                callback?.onClick(data)
            }
        }
    }

    interface CustomAdapterCallback {
        fun onClick(data: ListObject)
    }

    interface CustomAdapterDone {
        fun onClick(data: ListObject)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView1 = view.textView1
        val deleteButton = view.deleteButton
        val checkedButton = view.checkedButton
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            onBindViewHolder(holder, position)
    }
}