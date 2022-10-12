package com.camg_apps.axfactor.ui.menu.ui.ui_admin.users

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.data.model.User


class UserAdapter(
    private var values: List<User>
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvNombre.text = item.nombre
        holder.tvCorreo.text = item.correo
    }

    override fun getItemCount(): Int = values.size

    fun setData(listUser: List<User>){
        values = listUser
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvCorreo: TextView = view.findViewById(R.id.tvCorreo)

    }
}