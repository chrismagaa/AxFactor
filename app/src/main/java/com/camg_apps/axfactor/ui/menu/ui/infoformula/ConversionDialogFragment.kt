package com.camg_apps.axfactor.ui.menu.ui.infoformula

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.camg_apps.axfactor.R
import com.google.android.material.textfield.TextInputLayout

class ConversionDialogFragment() : DialogFragment() {

    internal lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, nuevosGramos: Double)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.dialog_convertir, null)

            builder.setView(view)
                    // Add action buttons
                    .setPositiveButton(R.string.fg_newformula_aceptar,
                            DialogInterface.OnClickListener { dialog, id ->
                                //get gramos
                                val gramos = view.findViewById<TextInputLayout>(R.id.textInputNewTotal).editText!!.text.toString().toDouble()
                                listener.onDialogPositiveClick(this, gramos)
                            })
                    .setNegativeButton(R.string.txt_cancelar,
                            DialogInterface.OnClickListener { dialog, id ->
                                listener.onDialogNegativeClick(this)
                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }


}