package com.camg_apps.axfactor.ui.menu.ui.infoformula

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camg_apps.axfactor.data.AppRepository
import com.camg_apps.axfactor.data.model.Formula
import com.camg_apps.axfactor.data.model.Material
import kotlinx.coroutines.launch

class InfoFormulaViewModel: ViewModel() {
    private var appRepository: AppRepository? = AppRepository()

    fun convertirFormula(nuevosGramos: Double, formula: Formula): Formula {
        if(formula.materiales!!.isNotEmpty()){
            //get total gramos
            var totalGramos = 0.0
            formula.materiales.forEach{
               totalGramos+=it.gramos!!
            }

             formula.materiales.forEach {
                 it.gramos = (it.gramos!!*totalGramos)/nuevosGramos
             }
         }
        return formula
    }

    fun setNewMaterial(material: Material, userId: String, codigo: String){
        viewModelScope.launch {
            appRepository!!.writeNewMaterial(material, userId, codigo)
        }
    }


    fun deleteMaterial(userId: String, codigo: String, key: String){
        viewModelScope.launch {
            appRepository!!.deleteMaterial(userId, codigo, key)
        }
    }
}