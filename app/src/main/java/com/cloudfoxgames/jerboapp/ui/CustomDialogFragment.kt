package com.cloudfoxgames.jerboapp.ui

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cloudfoxgames.jerboapp.R
import com.cloudfoxgames.jerboapp.db.entity.DinoEntity
import com.cloudfoxgames.jerboapp.viewmodel.SavedDinosViewModel
import kotlinx.android.synthetic.main.save_dialog.view.*

class CustomDialogFragment(val bundle: Bundle?, val option: Int) : DialogFragment() {
    var dinoId : Int = -1
    lateinit var dino: DinoEntity
    private lateinit var viewModel: SavedDinosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(SavedDinosViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        when (option) {
            1 -> return showSaveDialog()
            2 -> return showDeleteIdDialog()
            3 -> return showDeleteAllDialog()
            else -> return showEditDialog()
        }
    }

    private fun showEditDialog(): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.save_dialog, null)

            isCancelable = false

            builder.setView(view)
                .setTitle(getString(R.string.edit_dialog_title))
                .setMessage(getString(R.string.part1_edit_dialog_message) + dino.name + getString(R.string.part2_edit_dialog_message))
                .setPositiveButton(R.string.yes,
                    DialogInterface.OnClickListener { dialog, id ->
                        val dinoName : String
                        if (view.dinoNameET.text.isNullOrEmpty()) {
                            dinoName = dino.name
                        } else {
                            dinoName = view.dinoNameET.text.toString()
                            val newDino = DinoEntity(dinoName,dino.type,dino.image,dino.lvl,dino.id,dino.hpPoints,dino.staminaPoints,
                                dino.oxygenPoints,dino.foodPoints,dino.weightPoints,dino.damagePoints,dino.wastedPoints,dino.speedPoints)
                            viewModel.update(newDino)
                        }
                    })
                .setNegativeButton(R.string.no,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun showDeleteAllDialog(): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            isCancelable = false
            builder.setMessage(getString(R.string.delete_all_dialog_message))
                .setPositiveButton(R.string.yes,
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.deleteAll()
                    })
                .setNegativeButton(R.string.no,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun showDeleteIdDialog(): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            isCancelable = false
            builder.setMessage(getString(R.string.delete_dialog_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes),
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.deleteById(dinoId)
                    })
                .setNegativeButton(getString(R.string.no),
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun showSaveDialog() : Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.save_dialog, null)

            isCancelable = false

            builder.setView(view)
                .setTitle(R.string.adding)
                .setMessage(R.string.add_dialog_message)
                .setPositiveButton(R.string.yes,
                    DialogInterface.OnClickListener { dialog, id ->
                        val dinoName : String
                        if (view.dinoNameET.text.isNullOrEmpty()) {
                            dinoName = bundle!!.getString("type").toString()
                        } else {
                            dinoName = view.dinoNameET.text.toString()
                        }
                        val intent = Intent(activity, SavedDinoActivity::class.java)
                        bundle!!.putString("name", dinoName)
                        intent.putExtra("dino", bundle)
                        startActivity(intent)
                    })
                .setNegativeButton(R.string.no,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    internal fun setDinoId(id : Int) {
        dinoId = id
    }

    internal fun setDino(dino: DinoEntity) {
        this.dino = dino
    }
}