package com.camg_apps.axfactor.ui.menu.ui.ui_admin.laboratorios

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camg_apps.axfactor.data.AppRepository
import kotlinx.coroutines.launch

class LaboratorioViewModel: ViewModel() {
    private var repository: AppRepository = AppRepository()
    var laboratoriosLiveData: MutableLiveData<List<String>> = repository.laboratoriosLiveData

    fun getLaboratorios() {
        viewModelScope.launch {
            repository.getLaboratorios()
        }
    }
}