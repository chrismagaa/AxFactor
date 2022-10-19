package com.camg_apps.axfactor.ui.menu.ui.newformula

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.data.model.Tint

class MaterialNewAdapter : RecyclerView.Adapter<MaterialNewAdapter.ViewHolder>(){

    private var values: ArrayList<Tint> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_new_material, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.etCode.setText(item.name.toString())
        holder.etWeight.setText(item.weight.toString())

        holder.etCode.addTextChangedListener {
            if(it != null){
                values[position].name = it.toString()
            }
        }

        holder.etWeight.addTextChangedListener {
            if(!it.isNullOrBlank()){
                values[position].weight = it.toString().toDouble()
            }
        }

    }




    override fun getItemCount(): Int = values.size

    fun setData(newValues: ArrayList<Tint>){
        this.values = newValues
        notifyItemInserted(values.size)
    }

    fun getValues(): ArrayList<Tint>{
        return values
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val etCode: EditText = view.findViewById(R.id.etCode)
        val etWeight: EditText = view.findViewById(R.id.etWeight)

    }






}