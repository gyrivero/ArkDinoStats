package com.example.arkdinostats.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.arkdinostats.model.Dino
import com.example.arkdinostats.R

class DinoRecyclerViewAdapter(
    var values: MutableList<Dino>,
    var context: Context?
) : RecyclerView.Adapter<DinoRecyclerViewAdapter.ViewHolder>(), Filterable {
    var fullList = ArrayList<Dino>(values)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.dinoNameTV.text = item.name
        holder.dinoIV.setImageResource(item.image)

        holder.dinoIV.setOnClickListener(View.OnClickListener { it ->
            val dinoName = item.name
            val fragmentManager : FragmentManager = (context as FragmentActivity).supportFragmentManager
            val fragment : Fragment = CalculatorFragment()
            val bundle = Bundle()
            bundle.putString("name",dinoName)
            fragment.arguments = bundle
            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,fragment)
                .commit()

        })
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