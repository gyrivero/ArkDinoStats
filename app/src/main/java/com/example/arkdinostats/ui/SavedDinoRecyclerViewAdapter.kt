package com.example.arkdinostats.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.arkdinostats.R
import com.example.arkdinostats.db.entity.DinoEntity

class SavedDinoRecyclerViewAdapter(var context: Context?) : RecyclerView.Adapter<SavedDinoRecyclerViewAdapter.ViewHolder>() {

    private var values = emptyList<DinoEntity>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_saved_dino_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      /*  val dinoIV: ImageView = view.findViewById(R.id.dinoIV)
        val dinoNameTV: TextView = view.findViewById(R.id.dinoNameTV) */
    }

    override fun onBindViewHolder(holder: SavedDinoRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = values[position]
        /*holder.dinoNameTV.text = item.name
        holder.dinoIV.setImageResource(item.image)*/
    }

    internal fun setDinos(dinos: List<DinoEntity>) {
        this.values = dinos
        notifyDataSetChanged()
    }
}