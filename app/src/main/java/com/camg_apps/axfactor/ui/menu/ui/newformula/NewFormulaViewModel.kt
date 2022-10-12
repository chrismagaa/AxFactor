package com.camg_apps.axfactor.ui.menu.ui.newformula

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camg_apps.axfactor.data.AppRepository
import com.camg_apps.axfactor.data.model.Armadora
import com.camg_apps.axfactor.data.model.Formula
import com.camg_apps.axfactor.data.model.Linea
import kotlinx.coroutines.launch

class NewFormulaViewModel: ViewModel() {
    private var appRepository: AppRepository? = null
    var armadorasLiveData: MutableLiveData<List<Armadora>>
    var lineasLiveData: MutableLiveData<List<Linea>>

    init {
        appRepository =  AppRepository()
        armadorasLiveData = appRepository!!.armadorasLiveData
        lineasLiveData = appRepository!!.lineasLiveData
    }

    fun getArmadoras() {
        viewModelScope.launch {
            appRepository!!.getArmadoras()
        }
    }

    fun getLineas() {
        viewModelScope.launch {
            appRepository!!.getLineas()
        }
    }

    fun setNewFormula(formula: Formula, userId: String, codigo: String) {
        viewModelScope.launch {
            appRepository!!.writeNewFormula(formula, userId, codigo)
        }
    }


}