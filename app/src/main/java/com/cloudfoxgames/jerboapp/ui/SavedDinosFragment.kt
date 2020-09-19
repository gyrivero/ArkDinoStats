package com.cloudfoxgames.jerboapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cloudfoxgames.jerboapp.R
import com.cloudfoxgames.jerboapp.viewmodel.SavedDinosViewModel

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_saved_dino_list, container, false)
        setHasOptionsMenu(true)

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
        viewModel.allDinos.observe(
            activity!!,
            Observer { it -> it?.let { dinoAdapter.setDinos(it) } })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_db, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all -> {
                val newFragment = CustomDialogFragment(null,3)
                newFragment.show(activity!!.supportFragmentManager,"dialog")
                return true
            }
            R.id.delete_id -> {
                if (dinoAdapter.getDinoId() < 0) {
                return super.onOptionsItemSelected(item)
                }
                val newFragment = CustomDialogFragment(null,2)
                newFragment.show(activity!!.supportFragmentManager,"dialog")
                newFragment.setDinoId(dinoAdapter.getDinoId())
                dinoAdapter.resetItemSelected(true)
                return true
            }
            R.id.edit -> {
                val newFragment = CustomDialogFragment(null,4)
                newFragment.show(activity!!.supportFragmentManager,"dialog")
                if (dinoAdapter.getDino() == null) {
                    return super.onOptionsItemSelected(item)
                }
                newFragment.setDino(dinoAdapter.getDino()!!)
                dinoAdapter.resetItemSelected(true)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}