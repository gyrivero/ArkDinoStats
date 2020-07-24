package com.example.arkdinostats.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.ActionBarContainer
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.arkdinostats.model.Dino
import com.example.arkdinostats.R
import kotlinx.android.synthetic.main.fragment_item_list.*

class DinoFragment : Fragment()  {

    private var columnCount = 3
    private lateinit var dinoAdapter : DinoRecyclerViewAdapter
    private var dinoList : List<Dino> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        setHasOptionsMenu(true)


        dinoList = Dino.allDinos().sortedBy { dino -> dino.name }

        dinoAdapter =
            DinoRecyclerViewAdapter(Dino.allDinos() as MutableList<Dino>)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = dinoAdapter
            }
        }
        return view
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem : MenuItem = menu.findItem(R.id.dino_search)
        val searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                dinoAdapter.filter.filter(newText)
                return false
            }

        })
        super.onPrepareOptionsMenu(menu)
    }



    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            DinoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}