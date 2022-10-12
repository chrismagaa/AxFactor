package com.camg_apps.axfactor.ui.menu.ui.new_inventario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.data.model.Formula

class FormulaInventarioAdapter (
    private val values: List<Formula>
        )
    : RecyclerView.Adapter<FormulaInventarioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_formula_inventario, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvCodigo.text = item.codigo
        holder.tvLinea.text = item.linea
        holder.ivColor.setBackgroundColor(item.color!!.toInt())
        holder.tvGramos.text = item.codigo
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCodigo: TextView = view.findViewById(R.id.textViewCodigo)
        val tvLinea: TextView = view.findViewById(R.id.textViewMarca)
        val ivColor: ImageView = view.findViewById(R.id.imageViewColor)
        val tvGramos: TextView = view.findViewById(R.id.textViewGramosM)
    }
}
