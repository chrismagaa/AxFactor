package com.camg_apps.axfactor.ui.menu.ui.ui_admin.laboratorios

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.ui.menu.ui.ui_admin.users.UserFragment


class LaboratorioAdapter(
        private var values: List<String>,
        private val navController: NavController)
    : RecyclerView.Adapter<LaboratorioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_laboratorio, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvLaboratorio.text = item
        holder.cvLaboratorio.setOnClickListener{
            val bundle = bundleOf(UserFragment.ARG_LABORATORIO to item)
            navController.navigate(R.id.action_nav_laboratorios_to_userFragment, bundle)
        }
    }

    fun setValues(listLaboratorios: List<String>){
        values = listLaboratorios
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvLaboratorio: TextView = view.findViewById(R.id.tvLaboratorio)
        val cvLaboratorio: CardView = view.findViewById(R.id.cvLaboratorio)

    }
}