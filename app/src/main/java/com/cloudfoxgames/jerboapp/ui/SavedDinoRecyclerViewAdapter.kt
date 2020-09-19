package com.cloudfoxgames.jerboapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cloudfoxgames.jerboapp.R
import com.cloudfoxgames.jerboapp.db.entity.DinoEntity
import com.cloudfoxgames.jerboapp.model.Dino

class SavedDinoRecyclerViewAdapter(var context: Context?) :
    RecyclerView.Adapter<SavedDinoRecyclerViewAdapter.ViewHolder>() {

    var values = mutableListOf<DinoEntity>()
    var itemCheck = false
    var itemID = -1
    lateinit var itemView : View
    var dino: DinoEntity? = null


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
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (itemCheck && itemID == item.id) {
                it.setBackgroundColor(context!!.resources.getColor(android.R.color.white,null))
                itemCheck = false
                resetItemSelected(false)
            } else if (!itemCheck) {
                it.setBackgroundColor(context!!.resources.getColor(R.color.colorAccent,null))
                itemCheck = true
                itemView = it
                itemID = item.id!!
                dino = item
            }
        })
    }

    internal fun setDinos(dinos: List<DinoEntity>) {
        this.values = dinos as MutableList<DinoEntity>
        notifyDataSetChanged()
    }

    internal fun getDinoId(): Int {
        return this.itemID
    }

    internal fun resetItemSelected(itemDeleted : Boolean) {
        if (itemDeleted) {
            itemView.setBackgroundColor(context!!.resources.getColor(android.R.color.white,null))
        }
        itemID = -1
        itemCheck = false
        dino = null
    }

    internal fun getDino() : DinoEntity? {
        return dino
    }
}