package com.camg_apps.axfactor.ui.menu.ui.new_inventario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camg_apps.axfactor.data.AppRepository
import com.camg_apps.axfactor.data.model.Formula
import com.camg_apps.axfactor.data.model.Linea
import kotlinx.coroutines.launch
import java.text.Normalizer

class NewInventarioViewModel: ViewModel() {
    private var repository: AppRepository = AppRepository()
    var lineasLiveData: MutableLiveData<List<Linea>> = repository.lineasLiveData
    var formulasLiveData: MutableLiveData<List<Formula>> = repository.formulasLiveData


    fun getLineas() {
        viewModelScope.launch {
            repository.getLineas()
        }
    }

    fun getFormulas() {
        viewModelScope.launch {
            repository.getFormulasOneTime()
        }
    }

    fun getFormulasWithLinea(lineaSelected: String): ArrayList<Formula> {
        var filterFormulas = arrayListOf<Formula>()
        formulasLiveData.value!!.forEach {
            if(it.linea == lineaSelected){
                filterFormulas.add(it)
            }
        }
        return filterFormulas
    }


}