package com.example.arkdinostats.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.arkdinostats.R
import com.example.arkdinostats.model.Dino
import kotlinx.android.synthetic.main.fragment_calculator.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "name"
private const val ARG_PARAM2 = "param2"

class CalculatorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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

        val displayMetrics = context!!.resources.displayMetrics
        val dpi = displayMetrics.densityDpi
        val density = displayMetrics.density
        val scaledDensity = displayMetrics.scaledDensity
        val widthDpi = displayMetrics.widthPixels / density

        Toast.makeText(activity, "dpi: $dpi / density: $density / scaledDensity: $scaledDensity / widthDpi: $widthDpi", Toast.LENGTH_SHORT).show()
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