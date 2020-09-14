package com.example.arkdinostats.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arkdinostats.R
import com.example.arkdinostats.db.entity.DinoEntity
import com.example.arkdinostats.model.Dino
import com.example.arkdinostats.viewmodel.SavedDinosViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SavedDinosFragment : Fragment() {
    private lateinit var dinoAdapter: SavedDinoRecyclerViewAdapter

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
        val searchView : SearchView = activity!!.findViewById(R.id.dinoSearch)
        setHasOptionsMenu(true)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                dinoAdapter.filter(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                dinoAdapter.filter(newText!!)
                return true
            }

        })

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_db, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all) {
            viewModel.deleteAll()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

}