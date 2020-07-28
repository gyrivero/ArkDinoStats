package com.example.arkdinostats.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.example.arkdinostats.R
import com.example.arkdinostats.model.Dino
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

        view.checkBtn.setOnClickListener(View.OnClickListener {
            if (!view.lvlET.text.isNullOrEmpty()){
                average = view.lvlET.text.toString().toInt()
                average = (average-1)/7
                view.averageTV.setText("Average: $average")
                checkStats()
            }
            else {
                lvlET.setError("Please indicate the level")
            }
        })

        val dinoList : List<Dino> = ArrayList<Dino>(Dino.allDinos())
        for (dino in dinoList) {
            if (dino.name.equals(param1)) {
                view.nameTV.text = dino.name
                view.dinoIV.setImageResource(dino.image)
                break
            }
        }
        return view
    }

    private fun checkStats() {
        if (!hpNumberET.text.isNullOrEmpty()) {
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
        else {
            hpNumberET.setError("Please indicate the Health Points")
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
