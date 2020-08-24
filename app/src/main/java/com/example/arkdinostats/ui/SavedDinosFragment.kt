package com.example.arkdinostats.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arkdinostats.R
import com.example.arkdinostats.viewmodel.SavedDinosViewModel

class SavedDinosFragment : Fragment() {
    private lateinit var dinoAdapter: SavedDinoRecyclerViewAdapter
    private lateinit var dinoViewModel: SavedDinosViewModel

    companion object {
        fun newInstance() = SavedDinosFragment()
    }

    private lateinit var viewModel: SavedDinosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(SavedDinosViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val view  = inflater.inflate(R.layout.fragment_saved_dino_list, container, false)

        dinoAdapter = SavedDinoRecyclerViewAdapter(context)
        loadDinos()

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = dinoAdapter
            }
        }

        return view
    }

    private fun loadDinos() {
        viewModel.allDinos.observe(activity!!, Observer { it -> it?.let { dinoAdapter.setDinos(it) } })
    }

}