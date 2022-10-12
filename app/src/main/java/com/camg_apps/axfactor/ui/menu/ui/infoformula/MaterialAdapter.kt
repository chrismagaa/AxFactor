package com.camg_apps.axfactor.ui.menu.ui.infoformula

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.common.MyUtils
import com.camg_apps.axfactor.data.model.Material
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout


class MaterialAdapter(
        private var values: ArrayList<Material>,
        private val ctx: Context,
        private val vmInfoFormula: InfoFormulaViewModel,
        private val codigo: String?
) : RecyclerView.Adapter<MaterialAdapter.ViewHolder>(){

    private  var listener: View.OnLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_info, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvCodigo.text = item.codigo
        holder.tvGramos.text = item.gramos.toString()


        holder.clInfo.setOnClickListener {
            showDialogEditMaterial(item)
        }

        holder.clInfo.setOnLongClickListener{
            showDialogDeleteMaterial(codigo!!, item.key, position)
            true
        }
    }

    private fun showDialogEditMaterial(item: Material) {
    val dialog =  MaterialAlertDialogBuilder(ctx)
            .setView(R.layout.dialog_edit_material)
            .setPositiveButton(ctx.getString(R.string.fg_newformula_aceptar)){dialog, which ->
                //TODO aqui faltin
            }
            .setNegativeButton(ctx.getString(R.string.txt_cancelar)){dialog, which ->
                dialog.dismiss()
            }.show()

        dialog.findViewById<TextInputLayout>(R.id.textInputEditCodigo)!!.editText!!.setText(item.codigo)
        dialog.findViewById<TextInputLayout>(R.id.textInputEditGramos)!!.editText!!.setText(item.gramos.toString())


    }

    private fun showDialogDeleteMaterial(codigo: String, key: String?, item: Int) {
        MaterialAlertDialogBuilder(ctx)
                .setTitle(ctx.getString(R.string.txt_eliminar_material))
                .setMessage(ctx.getString(R.string.txt_seguro_eliminar))
                .setPositiveButton(ctx.getString(R.string.fg_newformula_aceptar)) { dialog, which ->
                    vmInfoFormula.deleteMaterial(MyUtils.getUserUid(ctx), codigo, key!!)
                    values.removeAt(item)
                    notifyItemRemoved(item)
                }
                .setNegativeButton(ctx.getString(R.string.txt_cancelar)) { dialog, which ->
                    dialog.dismiss()
                }
                .show()
    }


    override fun getItemCount(): Int = values.size

    fun setData(newValues: ArrayList<Material>){
        this.values = newValues
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCodigo: TextView = view.findViewById(R.id.textViewCodigo)
        val tvGramos: TextView = view.findViewById(R.id.textViewGramosM)
        val clInfo: ConstraintLayout = view.findViewById(R.id.clInfo)

    }




}