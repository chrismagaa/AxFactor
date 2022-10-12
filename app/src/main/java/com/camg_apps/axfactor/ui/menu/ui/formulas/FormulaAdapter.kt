package com.camg_apps.axfactor.ui.menu.ui.formulas

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.data.model.Formula
import com.camg_apps.axfactor.ui.menu.ui.infoformula.InfoFormulaFragment


class FormulaAdapter(
        private var values: List<Formula>,
        private val navController: NavController
) : RecyclerView.Adapter<FormulaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_formula, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvMarca.text = item.armadora
        holder.tvCodigo.text = item.codigo
        holder.tvLinea.text = item.linea
        holder.ivColor.setBackgroundColor(item.color!!.toInt())

        holder.cvFormula.setOnClickListener{
            val bundle = bundleOf(InfoFormulaFragment.PARAMETER_FORMULA to item)
            navController.navigate(R.id.action_nav_formulas_to_infoFragmentFragment, bundle)
        }
    }

    fun setData(listFormulas: List<Formula>){
        values = listFormulas
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMarca: TextView = view.findViewById(R.id.textViewLinea)
        val tvCodigo: TextView = view.findViewById(R.id.textViewCodigo)
        val tvLinea: TextView = view.findViewById(R.id.textViewMarca)
        val ivColor: ImageView = view.findViewById(R.id.imageViewColor)
        val cvFormula: CardView = view.findViewById(R.id.cvFormula)
    }
}