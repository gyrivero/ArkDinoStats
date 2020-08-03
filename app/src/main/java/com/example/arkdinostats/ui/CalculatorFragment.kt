package com.example.arkdinostats.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.arkdinostats.R
import com.example.arkdinostats.Utils
import com.example.arkdinostats.model.Dino
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlinx.android.synthetic.main.fragment_calculator.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "dino"
private const val ARG_PARAM2 = "param2"

class CalculatorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Dino? = null
    private var param2: String? = null
    var average : Int = 0
    lateinit var actualDino : Dino

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Dino?
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        actualDino = param1!!
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
                if (actualDino.name.contains("Astrocetus")) {
                    average = (average-1)/6
                } else {
                    average = (average-1)/7
                }
                view.averageTV.setText("Average: $average")
                checkPoints()
                checkStats()
            }
        })

        view.nameTV.text = actualDino.name
        view.dinoIV.setImageResource(actualDino.image)
        view.hpNumberET.setText(actualDino.baseHP.toString())
        view.staminaNumberET.setText(actualDino.baseStamina.toString())
        view.damageNumberET.setText(actualDino.baseDamage.toString())
        view.foodNumberET.setText(actualDino.baseFood.toString())
        view.oxygenNumberET.setText(actualDino.baseOxygen.toString())
        view.speedNumberET.setText(actualDino.baseSpeed.toString())
        view.torpidityNumberET.setText(actualDino.baseTorpidity.toString())
        view.weightNumberET.setText(actualDino.baseWeight.toString())
        return view
    }

    private fun checkPoints() {
        if (!hpPoints.text.isNullOrEmpty()) {
            hpPoints.text
        }
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
            startCalculate()
        }
    }

    private fun startCalculate() {
        val pointsHP : Int
        val pointsStamina : Int
        val pointsWeight : Int
        val pointsDamage : Int
        val pointsSpeed : Int
        val pointsOxygen : Int
        val pointsFood : Int
        val pointsTorpidity : Int
        if (statusRG.checkedRadioButtonId == R.id.wildRB) {
            pointsHP = calculateWild(hpNumberET.text.toString().toFloat(),actualDino.baseHP,actualDino.iwHP)
            pointsStamina = calculateWild(staminaNumberET.text.toString().toFloat(),actualDino.baseStamina,actualDino.iwStamina)
            pointsWeight = calculateWild(weightNumberET.text.toString().toFloat(),actualDino.baseWeight,actualDino.iwWeight)
            pointsDamage = calculateWild(damageNumberET.text.toString().toFloat()/100,actualDino.baseDamage/100,actualDino.iwDamage)
            pointsSpeed = calculateWild(speedNumberET.text.toString().toFloat()/100,actualDino.baseSpeed/100,actualDino.iwSpeed)
            pointsOxygen = calculateWild(oxygenNumberET.text.toString().toFloat(),actualDino.baseOxygen,actualDino.iwOxygen)
            pointsFood = calculateWild(foodNumberET.text.toString().toFloat(),actualDino.baseFood,actualDino.iwFood)
            pointsTorpidity = calculateWild(torpidityNumberET.text.toString().toFloat(),actualDino.baseTorpidity,actualDino.iwTorpidity)
        } else if (statusRG.checkedRadioButtonId == R.id.tamedRB) {
            pointsHP = calculateTamed(hpNumberET.text.toString().toFloat(),actualDino.taHP,0.14F,actualDino.tmHP
                ,0.44F,actualDino.baseHP,actualDino.tbhm,actualDino.iwHP,effectET.text.toString().toFloat()/100)
            pointsStamina = calculateTamed(staminaNumberET.text.toString().toFloat(),actualDino.taStamina,1F,actualDino.tmStamina
                ,1F,actualDino.baseStamina,actualDino.iwStamina,effectET.text.toString().toFloat()/100)
            pointsWeight = calculateTamed(weightNumberET.text.toString().toFloat(),actualDino.taWeight,1F,actualDino.tmWeight
                ,1F,actualDino.baseWeight,actualDino.iwWeight,effectET.text.toString().toFloat()/100)
            pointsDamage = calculateTamed(damageNumberET.text.toString().toFloat()/100,actualDino.taDamage,0.14F,actualDino.tmDamage
                ,0.44F,actualDino.baseDamage/100,actualDino.iwDamage,effectET.text.toString().toFloat()/100)
            pointsOxygen = calculateTamed(oxygenNumberET.text.toString().toFloat(),actualDino.taOxygen,1F,actualDino.tmOxygen
                ,1F,actualDino.baseOxygen,actualDino.iwOxygen,effectET.text.toString().toFloat()/100)
            pointsSpeed = calculateTamed(speedNumberET.text.toString().toFloat()/100,actualDino.taSpeed,1F,actualDino.tmSpeed
                ,1F,actualDino.baseSpeed/100,actualDino.iwSpeed,effectET.text.toString().toFloat()/100)
            pointsTorpidity = calculateTamed(torpidityNumberET.text.toString().toFloat(),actualDino.taTorpidity,1F,actualDino.tmTorpidity
                ,1F,actualDino.baseTorpidity,actualDino.iwTorpidity,effectET.text.toString().toFloat()/100)
            pointsFood = calculateTamed(foodNumberET.text.toString().toFloat(),actualDino.taFood,1F,actualDino.tmFood
                ,1F,actualDino.baseFood,actualDino.iwFood,effectET.text.toString().toFloat()/100)

        } else {
            pointsHP = calculateBreed(hpNumberET.text.toString().toFloat(),actualDino.taHP,0.14F,actualDino.tmHP
                ,0.44F,actualDino.baseHP,actualDino.tbhm,actualDino.iwHP,1.0F,imprintET.text.toString().toFloat()/100)
            pointsDamage = calculateBreed(damageNumberET.text.toString().toFloat()/100,actualDino.taDamage,0.14F,actualDino.tmDamage
                ,0.44F,actualDino.baseDamage/100,actualDino.iwDamage,1.0F,imprintET.text.toString().toFloat()/100)
            pointsFood = calculateBreed(foodNumberET.text.toString().toFloat(),actualDino.taFood,1F,actualDino.tmFood
                ,1F,actualDino.baseFood,actualDino.iwFood,1.0F,imprintET.text.toString().toFloat()/100)
            pointsSpeed = calculateBreed(speedNumberET.text.toString().toFloat()/100,actualDino.taSpeed,1F,actualDino.tmSpeed
                ,1F,actualDino.baseSpeed/100,actualDino.iwSpeed,1.0F,imprintET.text.toString().toFloat()/100)
            pointsWeight = calculateBreed(weightNumberET.text.toString().toFloat(),actualDino.taWeight,1F,actualDino.tmWeight
                ,1F,actualDino.baseWeight,actualDino.iwWeight,1.0F,imprintET.text.toString().toFloat()/100)
            pointsOxygen = calculateWild(oxygenNumberET.text.toString().toFloat(),actualDino.baseOxygen,actualDino.iwOxygen)
            pointsStamina = calculateWild(staminaNumberET.text.toString().toFloat(),actualDino.baseStamina,actualDino.iwStamina)
            pointsTorpidity = calculateBreed(torpidityNumberET.text.toString().toFloat(),actualDino.taTorpidity,1F,actualDino.tmTorpidity
                ,1F,actualDino.baseTorpidity,actualDino.iwTorpidity,1.0F,imprintET.text.toString().toFloat()/100)
        }

        val totalPoints = pointsDamage+pointsHP+pointsFood+pointsOxygen+pointsSpeed+pointsStamina+pointsWeight
        val isValuesOk = checkValues(totalPoints,pointsTorpidity,lvlET.text.toString().toInt())
        checkQuality(hpPoints,pointsHP,isValuesOk)
        checkQuality(staminaPoints,pointsStamina,isValuesOk)
        checkQuality(weigthPoints,pointsWeight,isValuesOk)
        checkQuality(damagePoints,pointsDamage,isValuesOk)
        checkQuality(speedPoints,pointsSpeed,isValuesOk)
        checkQuality(oxygenPoints,pointsOxygen,isValuesOk)
        checkQuality(foodPoints,pointsFood,isValuesOk)

    }

    private fun checkValues(totalPoints: Int,pointsTorpidity: Int, lvl: Int): Boolean {
        return (pointsTorpidity == totalPoints && pointsTorpidity == lvl-1)
    }

    private fun calculateBreed(
        v: Float,
        ta: Float,
        taM: Float,
        tm: Float,
        tmM: Float,
        b: Float,
        tbhm: Float,
        iw: Float,
        te: Float,
        ib : Float
    ): Int {
        var ibf : Float
        if (ib == 4000F) {
            ibf = (1).toFloat()
        } else {
            ibf = (1+ib*0.2*1).toFloat()
        }
        val tmf : Float
        if (tm<0) {
            tmf = 1 + (tm*tmM)
        } else {
            tmf = 1 + (tm*te*tmM)
        }
        if ((v-ta*taM*tmf-b*tbhm*tmf*ibf)/(b*iw*1*tbhm*tmf*ibf) <= 0F) {
            return 0
        }
        return Math.round((v-ta*taM*tmf-b*tbhm*tmf*ibf)/(b*iw*1*tbhm*tmf*ibf))
    }

    private fun calculateBreed(
        v: Float,
        ta: Float,
        taM: Float,
        tm: Float,
        tmM: Float,
        b: Float,
        iw: Float,
        te: Float,
        ib : Float
    ): Int {
        val ibf = (1+ib*0.2*1).toFloat()
        val tmf : Float
        if (tm<0) {
            tmf = 1 + (tm*tmM)
        } else {
            tmf = 1 + (tm*te*tmM)
        }
        if ((v-ta*taM*tmf-b*ibf*tmf)/(b*iw*1*tmf*ibf) <= 0F) {
            return 0
        }
        return Math.round((v-ta*taM*tmf-b*ibf*tmf)/(b*iw*1*tmf*ibf))
    }

    private fun calculateTamed(
        v: Float,
        ta: Float,
        taM: Float,
        tm: Float,
        tmM: Float,
        b: Float,
        tbhm: Float,
        iw: Float,
        te: Float
    ): Int {
        val tmf : Float
        if (tm<0) {
            tmf = 1 + (tm*tmM)
        } else {
            tmf = 1 + (tm*te*tmM)
        }
        if ((v-ta*taM*tmf-b*tbhm*tmf)/(b*iw*1*tbhm*tmf) <= 0F) {
            return 0
        }
        return Math.round((v-ta*taM*tmf-b*tbhm*tmf)/(b*iw*1*tbhm*tmf))
    }

    private fun calculateTamed(
        v: Float,
        ta: Float,
        taM: Float,
        tm: Float,
        tmM: Float,
        b: Float,
        iw: Float,
        te: Float
    ): Int {
        val tmf : Float
        if (tm<0) {
            tmf = 1 + (tm*tmM)
        } else {
            tmf = 1 + (tm*te*tmM)
        }
        if ((v-ta*taM*tmf-b*tmf)/(b*iw*1*tmf) <= 0F) {
            return 0
        }
        return Math.round((v-ta*taM*tmf-b*tmf)/(b*iw*1*tmf))
    }

    private fun checkQuality(pointsTV: TextView?, points: Int, isValuesOk : Boolean) {
        if (isValuesOk) {
            pointsTV!!.setText(points.toString())
            when {
                points in (average-(average/10))..(average+(average/10)) -> pointsTV.setBackgroundColor(resources.getColor(R.color.colorAccent,null))
                points < average-(average/10) -> pointsTV.setBackgroundColor(resources.getColor(R.color.colorPrimary,null))
                points > average+(average/10) -> pointsTV.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark,null))
            }
        } else {
            pointsTV!!.setText("0")
            pointsTV.setBackgroundColor(resources.getColor(android.R.color.holo_red_light,null))
        }
    }

    private fun calculateWild(v: Float, b: Float, iw: Float): Int {
        return Math.round((v-b)/(b*iw*1))
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
