package com.camg_apps.axfactor.ui.menu.ui.newformula

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camg_apps.axfactor.data.AppRepository
import com.camg_apps.axfactor.data.model.Formula
import kotlinx.coroutines.launch

class NewFormulaViewModel: ViewModel() {
    private var appRepository: AppRepository? = null

    var textResult = MutableLiveData<String>()

    init {
        appRepository =  AppRepository()
        textResult.value = ""
    }

    fun setNewFormula(formula: Formula, code: String) {
        viewModelScope.launch {
            appRepository!!.writeNewFormula(formula, code, {
                textResult.value = "Formula guardada con éxito"
            },{
                textResult.value = "Error al guardar la formula"
            })
        }
    }


}