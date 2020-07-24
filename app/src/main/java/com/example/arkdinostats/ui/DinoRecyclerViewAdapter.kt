package com.example.arkdinostats.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.example.arkdinostats.model.Dino
import com.example.arkdinostats.R

class DinoRecyclerViewAdapter(var values: MutableList<Dino>) : RecyclerView.Adapter<DinoRecyclerViewAdapter.ViewHolder>(), Filterable {
    var fullList = ArrayList<Dino>(values)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.dinoNameTV.setText(item.name)
        holder.dinoIV.setImageResource(item.image)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dinoIV: ImageView = view.findViewById(R.id.dinoIV)
        val dinoNameTV: TextView = view.findViewById(R.id.dinoNameTV)
    }

    override fun getFilter(): Filter {
        var searchfilter = object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList = ArrayList<Dino>()
                var filteredPattern = constraint.toString().toLowerCase().trim()

                if (constraint.isNullOrEmpty()){
                    filteredList.addAll(fullList)
                }
                else {
                    for (Dino in fullList) {
                        if (Dino.name.toLowerCase().contains(filteredPattern)) {
                            filteredList.add(Dino)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                values.clear()
                if (results != null) {
                    values.addAll(results.values as Collection<Dino>)
                }
                notifyDataSetChanged()
            }

        }
        return searchfilter
    }

}