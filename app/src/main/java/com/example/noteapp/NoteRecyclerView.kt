package com.example.noteapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteRecyclerView(var dataSet:ArrayList<Note>)
    :RecyclerView.Adapter<NoteRecyclerView.ViewHolder>() {
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var tvTitle:TextView?=null
        var tvDesc:TextView?=null
        var ivDel:ImageView?=null
        var ivUpd:ImageView?=null
        init {
            tvTitle=itemView.findViewById(R.id.tvTitle)
            tvDesc=itemView.findViewById(R.id.tvDesc)
            ivDel=itemView.findViewById(R.id.ivDelete)
            ivUpd=itemView.findViewById(R.id.ivUpdate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.note_ticket,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle!!.text=dataSet[position].title
        holder.tvDesc!!.text=dataSet[position].description
        holder.ivDel!!.setOnClickListener {
            val dbMng=DbManager(it.context)
            dbMng.delete("id=?", arrayOf(dataSet[position].id.toString()))
            dataSet.removeAt(position)
            notifyDataSetChanged()
        }
        holder.ivUpd!!.setOnClickListener {
            val intent=Intent(it.context,AddNoteActivity::class.java)
            intent.putExtra("id",dataSet[position].id)
            intent.putExtra("title",dataSet[position].title)
            intent.putExtra("desc",dataSet[position].description)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}