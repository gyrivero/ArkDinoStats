package com.example.arkdinostats.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.arkdinostats.R
import com.example.arkdinostats.Utils
import com.example.arkdinostats.model.Dino
import com.example.arkdinostats.model.JsonDino
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlinx.android.synthetic.main.fragment_calculator.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "name"
private const val ARG_PARAM2 = "param2"

class CalculatorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var average : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        view.effectET.visibility=View.GONE
        view.imprintET.visibility=View.GONE
        var statusIsSelected = false

        view.statusRG.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.wildRB -> {
                    view.effectET.visibility=View.GONE
                    view.imprintET.visibility=View.GONE
                    view.effectET.setText("")
                    statusIsSelected = true
                }
                R.id.breededRB -> {
                    view.effectET.visibility=View.GONE
                    view.imprintET.visibility=View.VISIBLE
                    view.effectET.setText("")
                    statusIsSelected = true
                }
                R.id.tamedRB -> {
                    view.effectET.visibility=View.VISIBLE
                    view.imprintET.visibility=View.GONE
                    view.imprintET.setText("")
                    statusIsSelected = true
                }
            }
        }

        view.checkBtn.setOnClickListener(View.OnClickListener {
            if (view.lvlET.text.isNullOrEmpty()) {
                lvlET.setError("Please indicate the level")
            } else if (!statusIsSelected) {
                lvlET.setError("Please indicate status")
            } else if (view.imprintET.visibility == View.VISIBLE && view.imprintET.text.isNullOrEmpty()) {
                imprintET.setError("Please indicate imprint percentage")
            } else if (view.effectET.visibility == View.VISIBLE && view.effectET.text.isNullOrEmpty()) {
                effectET.setError("Please indicate taming effectivenes percentage")
            } else {
                average = view.lvlET.text.toString().toInt()
                average = (average-1)/7
                view.averageTV.setText("Average: $average")
                checkStats()
            }
        })

        val dinoList : List<Dino> = ArrayList<Dino>(Dino.createDinos(Utils.jsonParse("values.json")))
        for (dino in dinoList) {
            if (dino.name.equals(param1)) {
                view.nameTV.text = dino.name
                view.dinoIV.setImageResource(dino.image)
                view.hpNumberET.setText(dino.baseHP.toString())
                view.staminaNumberET.setText(dino.baseStamina.toString())
                view.damageNumberET.setText(dino.baseDamage.toString())
                view.foodNumberET.setText(dino.baseFood.toString())
                view.oxygenNumberET.setText(dino.baseOxygen.toString())
                view.speedNumberET.setText(dino.baseSpeed.toString())
                view.torpidityNumberET.setText(dino.baseTorpidity.toString())
                view.weightNumberET.setText(dino.baseWeight.toString())
                break
            }
        }
        return view
    }

    private fun checkStats() {
        if (hpNumberET.text.isNullOrEmpty()) {
            hpNumberET.setError("Please indicate the Health Points")
        } else if (staminaNumberET.text.isNullOrEmpty()) {
            staminaNumberET.setError("Please indicate the Stamina Points")
        } else if (oxygenNumberET.text.isNullOrEmpty()) {
            oxygenNumberET.setError("Please indicate the Oxygen Points")
        } else if (foodNumberET.text.isNullOrEmpty()) {
            foodNumberET.setError("Please indicate the Food Points")
        } else if (weightNumberET.text.isNullOrEmpty()) {
            weightNumberET.setError("Please indicate the Weight Points")
        } else if (damageNumberET.text.isNullOrEmpty()) {
            damageNumberET.setError("Please indicate the Damage Points")
        } else if (speedNumberET.text.isNullOrEmpty()) {
            speedNumberET.setError("Please indicate the Speed Points")
        } else if (torpidityNumberET.text.isNullOrEmpty()) {
            torpidityNumberET.setError("Please indicate the Torpidity Points")
        } else {
            var hp = hpNumberET.text.toString().toFloat()
            if (hp>100.0F) {
                view!!.hpPoints.setText("1")
                view!!.hpPoints.setBackgroundColor(resources.getColor(R.color.colorAccent,null))
            } else if (hp<100.0F) {
                view!!.hpPoints.setText("3")
                view!!.hpPoints.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark,null))
            } else {
                view!!.hpPoints.setText("2")
                view!!.hpPoints.setBackgroundColor(resources.getColor(R.color.colorPrimary,null))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.close()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalculatorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalculatorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
