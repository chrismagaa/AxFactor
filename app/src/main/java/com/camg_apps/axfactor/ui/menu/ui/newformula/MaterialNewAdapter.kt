package com.camg_apps.axfactor.ui.menu.ui.newformula

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.data.model.Material

class MaterialNewAdapter : RecyclerView.Adapter<MaterialNewAdapter.ViewHolder>(){

    private var values: ArrayList<Material> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_new_material, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.etCode.setText(item.codigo.toString())
        holder.etWeight.setText(item.gramos.toString())




    }




    override fun getItemCount(): Int = values.size

    fun setData(newValues: ArrayList<Material>){
        this.values = newValues
        notifyItemInserted(values.size)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val etCode: EditText = view.findViewById(R.id.etCode)
        val etWeight: EditText = view.findViewById(R.id.etWeight)

    }






}