package com.camg_apps.axfactor.ui.menu.ui.convertir_formula

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.data.model.Material

class MaterialConvertirAdapter(
    private val values: List<Material>
) : RecyclerView.Adapter<MaterialConvertirAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_material_convertir, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvCodigo.text = item.codigo
        holder.tvGramos.text = item.gramos.toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCodigo: TextView = view.findViewById(R.id.textViewCodigo)
        val tvGramos: TextView = view.findViewById(R.id.textViewGramosM)

    }
}