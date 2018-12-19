package com.example.lab.calculator.View.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab.calculator.R

class HistoryAdapter(private val history: ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){

    class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var text: TextView? = null

        init{
            text = itemView.findViewById(R.id.text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_layout, parent, false)

        return HistoryViewHolder(itemView)
    }

    override fun getItemCount() = history.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.text?.text = history[position]
    }

}


