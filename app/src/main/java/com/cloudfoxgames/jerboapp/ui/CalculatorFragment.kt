package com.cloudfoxgames.jerboapp.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.cloudfoxgames.jerboapp.R
import com.cloudfoxgames.jerboapp.model.Dino
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlinx.android.synthetic.main.fragment_calculator.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "dino"
private const val ARG_PARAM2 = "param2"
private lateinit var mInterstitialAd: InterstitialAd
var pointsHP : Int = 0
var pointsStamina : Int = 0
var pointsWeight : Int = 0
var pointsDamage : Int = 0
var pointsSpeed : Int = 0
var pointsOxygen : Int = 0
var pointsFood : Int = 0
var pointsTorpidity : Int = 0
var wastedPoint : Int = 0
var isValuesOk = false
var checked = false
lateinit var menuItem : MenuItem

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

            mInterstitialAd = InterstitialAd(activity)
            mInterstitialAd.adUnitId = "ca-app-pub-3829761967318508/6486275931"
            mInterstitialAd.loadAd(AdRequest.Builder().build())
            mInterstitialAd.adListener = object : AdListener() {
                override fun onAdClosed() {
                    mInterstitialAd.loadAd(AdRequest.Builder().build())
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)
        view.averageTV.setTextColor(resources.getColor(android.R.color.black,null))
        view.constraintLayout.setBackgroundColor(resources.getColor(R.color.background,null))
        setHasOptionsMenu(true)

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
                lvlET.setError(getString(R.string.level_nedeed))
            } else if (!statusIsSelected) {
                lvlET.setError(getString(R.string.status_nedeed))
            } else if (view.imprintET.visibility == View.VISIBLE && view.imprintET.text.isNullOrEmpty()) {
                imprintET.setError(getString(R.string.imprint_nedeed))
            } else if (view.effectET.visibility == View.VISIBLE && view.effectET.text.isNullOrEmpty()) {
                effectET.setError(getString(R.string.effectiveness_nedeed))
            } else {
                average = view.lvlET.text.toString().toInt()
                if (actualDino.name.contains("Astrocetus")) {
                    average = (average - 1) / 6
                } else {
                    average = (average - 1) / 7
                }
                view.averageTV.setText(getString(R.string.average) + average)
                checkPoints()
                checkStats()
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                } else {
                    Toast.makeText(activity, "Doesn't loaded", Toast.LENGTH_SHORT).show()
                }
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
            hpNumberET.setError(getString(R.string.health_nedeed))
        } else if (staminaNumberET.text.isNullOrEmpty()) {
            staminaNumberET.setError(getString(R.string.stamina_nedeed))
        } else if (oxygenNumberET.text.isNullOrEmpty()) {
            oxygenNumberET.setError(getString(R.string.oxygen_nedeed))
        } else if (foodNumberET.text.isNullOrEmpty()) {
            foodNumberET.setError(getString(R.string.food_nedeed))
        } else if (weightNumberET.text.isNullOrEmpty()) {
            weightNumberET.setError(getString(R.string.wight_nedeed))
        } else if (damageNumberET.text.isNullOrEmpty()) {
            damageNumberET.setError(getString(R.string.damage_nedeed))
        } else if (speedNumberET.text.isNullOrEmpty()) {
            speedNumberET.setError(getString(R.string.speed_nedeed))
        } else if (torpidityNumberET.text.isNullOrEmpty()) {
            torpidityNumberET.setError(getString(R.string.torpidity_nedeed))
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
        wastedPoint = pointsTorpidity - totalPoints
        checkQuality(hpPoints,pointsHP,isValuesOk,wastedPoint)
        checkQuality(staminaPoints,pointsStamina,isValuesOk,wastedPoint)
        checkQuality(weigthPoints,pointsWeight,isValuesOk,wastedPoint)
        checkQuality(damagePoints,pointsDamage,isValuesOk,wastedPoint)
        checkQuality(speedPoints,pointsSpeed,isValuesOk,wastedPoint)
        checkQuality(oxygenPoints,pointsOxygen,isValuesOk,wastedPoint)
        checkQuality(foodPoints,pointsFood,isValuesOk,wastedPoint)
        if (!isValuesOk) {
            showDialog()
            checked = false
        } else {
            checked = true
            menuItem.setEnabled(true)
        }

    }

    private fun checkValues(totalPoints: Int,pointsTorpidity: Int, lvl: Int): Boolean {
        return (pointsTorpidity >= totalPoints && pointsTorpidity == lvl-1)
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
        val ibf : Float
        var taMCopy = taM
        if (ta<0) {
            taMCopy = 1F
        }
        if (ib <= 0F) {
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

        var calculateOK = false

        for (i in 0..600) {
            val resultValue =  (b* (1+i*iw*1)*tbhm*ibf+ta*taMCopy)*tmf
            val range = v-1..v+1
            if (resultValue in range) {
                calculateOK = true
            }
        }
        if (calculateOK) {
            if ((v-ta*taMCopy*tmf-b*tbhm*tmf*ibf)/(b*iw*1*tbhm*tmf*ibf) <= 0F) {
                return 0
            }
            return Math.round((v-ta*taMCopy*tmf-b*tbhm*tmf*ibf)/(b*iw*1*tbhm*tmf*ibf))
        }
        return 1000
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
        var taMCopy = taM
        if (ta<0) {
            taMCopy = 1F
        }
        if (tm<0) {
            tmf = 1 + (tm*tmM)
        } else {
            tmf = 1 + (tm*te*tmM)
        }

        var calculateOK = false

        for (i in 0..600) {
            val resultValue =  (b* (1+i*iw*1)*ibf+ta*taMCopy)*tmf
            val range = v-1..v+1
            if (resultValue in range) {
                calculateOK = true
            }
        }

        if (calculateOK) {
            if ((v-ta*taMCopy*tmf-b*ibf*tmf)/(b*iw*1*tmf*ibf) <= 0F) {
                return 0
            }
            return Math.round((v-ta*taMCopy*tmf-b*ibf*tmf)/(b*iw*1*tmf*ibf))
        }
        return 1000
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
        var taMCopy = taM
        if (ta<0) {
            taMCopy = 1F
        }
        if (tm<0) {
            tmf = 1 + (tm*tmM)
        } else {
            tmf = 1 + (tm*te*tmM)
        }

        var calculateOk = false

        for (i in 0..600) {
            val resultValue =  (b* (1+i*iw*1)*tbhm+ta*taMCopy)*tmf
            val range = v-1..v+1
            if (resultValue in range) {
                calculateOk = true
            }
        }

        if (calculateOk) {
            if ((v-ta*taM*tmf-b*tbhm*tmf)/(b*iw*1*tbhm*tmf) <= 0F) {
                return 0
            }
            return Math.round((v-ta*taM*tmf-b*tbhm*tmf)/(b*iw*1*tbhm*tmf))
        }
        return 1000
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
        var taMCopy = taM
        if (ta<0) {
            taMCopy = 1F
        }
        if (tm<0) {
            tmf = 1 + (tm*tmM)
        } else {
            tmf = 1 + (tm*te*tmM)
        }

        var calculateOK = false

        for (i in 0..500) {
            val resultValue =  (b* (1+i*iw*1)+ta*taMCopy)*tmf
            val range = v-1..v+1
            if (resultValue in range) {
                calculateOK = true
            }
        }

        if (calculateOK) {
            if ((v-ta*taM*tmf-b*tmf)/(b*iw*1*tmf) <= 0F) {
                return 0
            }
            return Math.round((v-ta*taM*tmf-b*tmf)/(b*iw*1*tmf))
        }
        return 1000
    }

    private fun checkQuality(
        pointsTV: TextView?,
        points: Int,
        isValuesOk: Boolean,
        wastedPoints: Int
    ) {
        if (isValuesOk) {
            if (wastedPoints != 0) {
                view!!.wastedPoints.setText(wastedPoints.toString())
            }
        }
        if (points==1000) {
            pointsTV!!.setText("!!")
            pointsTV.setBackgroundColor(resources.getColor(android.R.color.holo_red_light,null))
        } else {
            pointsTV!!.setText(points.toString())
            when {
                points in (average-(average/10))..(average+(average/10)) -> pointsTV.setTextColor(resources.getColor(R.color.average,null))
                points < average-(average/10) -> pointsTV.setTextColor(resources.getColor(R.color.belowAverage,null))
                points > average+(average/10) -> pointsTV.setTextColor(resources.getColor(R.color.overAverage,null))
            }
        }
    }

    private fun showDialog() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        builder!!.setMessage(getString(R.string.stats_error_dialog_message))
            .setTitle(getString(R.string.error))
            .setCancelable(false)
            .setNeutralButton(getString(R.string.ok), DialogInterface.OnClickListener{ dialog, which -> dialog.dismiss() })
        val dialog: AlertDialog? = builder.create()
        dialog!!.show()
    }

    private fun showAddDialog() {
        val bundle = Bundle()
        bundle.putInt("image",actualDino.image)
        bundle.putInt("hp", pointsHP)
        bundle.putInt("stamina", pointsStamina)
        bundle.putInt("oxygen", pointsOxygen)
        bundle.putInt("food", pointsFood)
        bundle.putInt("weight", pointsWeight)
        bundle.putInt("damage", pointsDamage)
        bundle.putInt("speed", pointsSpeed)
        bundle.putInt("lvl",lvlET.text.toString().toInt())
        bundle.putInt("wasted", wastedPoint)
        bundle.putString("type",actualDino.name)

        val newFragment = CustomDialogFragment(bundle,1)
        newFragment.show(activity!!.supportFragmentManager,"dialog")
    }

    private fun calculateWild(v: Float, b: Float, iw: Float): Int {

        var calculateOK = false

        for (i in 0..500) {
            val resultValue = b* (1+i*iw*1)
            val range = v-1..v+1
            if (resultValue in range) {
                calculateOK = true
            }
        }

        if (calculateOK) {
            return Math.round((v-b)/(b*iw*1))
        }
        return 1000
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_calculator,menu)
        menuItem = menu.findItem(R.id.dino_add)
        menuItem.setEnabled(false)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.dino_add && checked) {
            showAddDialog()
            item.setEnabled(false)
            checked = false
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
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
