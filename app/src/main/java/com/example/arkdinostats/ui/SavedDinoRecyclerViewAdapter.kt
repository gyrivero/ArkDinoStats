package com.example.arkdinostats.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.arkdinostats.R
import com.example.arkdinostats.db.entity.DinoEntity
import org.w3c.dom.Text

class SavedDinoRecyclerViewAdapter(var context: Context?) :
    RecyclerView.Adapter<SavedDinoRecyclerViewAdapter.ViewHolder>() {

    var values = mutableListOf<DinoEntity>()
    var itemsCopy = ArrayList<DinoEntity>(values)
    //var fullList = ArrayList<DinoEntity>(values)


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
        val dinoIV: ImageView = view.findViewById(R.id.dinoIV)
        val dinoNameTV: TextView = view.findViewById(R.id.dinoNameTV)
        val dinoLvlTV: TextView = view.findViewById(R.id.lvlTV)
        val dinoHPTV: TextView = view.findViewById(R.id.healthPointsTV)
        val dinoStaminaTV: TextView = view.findViewById(R.id.energyPointsTV)
        val dinoOxygenTV: TextView = view.findViewById(R.id.oxygenPointsTV)
        val dinoFoodTV: TextView = view.findViewById(R.id.foodPointsTV)
        val dinoSpeedTV: TextView = view.findViewById(R.id.speedPointsTV)
        val dinoDamageTV: TextView = view.findViewById(R.id.damagePointsTV)
        val dinoWeightTV: TextView = view.findViewById(R.id.weightPointsTV)
        val dinoWastedTV: TextView = view.findViewById(R.id.wastedPointsTV)
    }

    override fun onBindViewHolder(holder: SavedDinoRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = values[position]
        holder.dinoNameTV.text = item.name
        holder.dinoIV.setImageResource(item.image)
        holder.dinoLvlTV.text = item.lvl.toString()
        holder.dinoHPTV.text = item.hpPoints.toString()
        holder.dinoStaminaTV.text = item.staminaPoints.toString()
        holder.dinoOxygenTV.text = item.oxygenPoints.toString()
        holder.dinoFoodTV.text = item.foodPoints.toString()
        holder.dinoSpeedTV.text = item.speedPoints.toString()
        holder.dinoDamageTV.text = item.damagePoints.toString()
        holder.dinoWeightTV.text = item.weightPoints.toString()
        holder.dinoWastedTV.text = item.wastedPoints.toString()
    }

    internal fun setDinos(dinos: List<DinoEntity>) {
        this.values = dinos as MutableList<DinoEntity>
        notifyDataSetChanged()
    }

    fun filter(text: String) {
        if (text.isEmpty()) {
            values.clear()
            values.addAll(itemsCopy)
        } else {
            var result = ArrayList<DinoEntity>()
            var textLower = text.toLowerCase()
            for (DinoEntity in itemsCopy) {
                if (DinoEntity.name.contains(textLower)) {
                    result.add(DinoEntity)
                }
            }
            values.clear()
            values.addAll(result)
        }
        notifyDataSetChanged()
    }

    /*override fun getFilter(): Filter {
        var searchfilter = object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList = ArrayList<DinoEntity>()
                var filteredPattern = constraint.toString().toLowerCase().trim()

                if (constraint.isNullOrEmpty()){
                    filteredList.addAll(fullList)
                }
                else {
                    for (DinoEntity in fullList) {
                        if (DinoEntity.name.toLowerCase().contains(filteredPattern)) {
                            filteredList.add(DinoEntity)
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
                    values.addAll(results.values as Collection<DinoEntity>)
                }
                notifyDataSetChanged()
            }

        }
        return searchfilter
    }*/
}