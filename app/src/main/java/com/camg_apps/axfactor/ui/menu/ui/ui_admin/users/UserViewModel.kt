package com.camg_apps.axfactor.ui.menu.ui.ui_admin.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camg_apps.axfactor.data.AppRepository
import com.camg_apps.axfactor.data.model.User
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private var repository: AppRepository = AppRepository()
    var usersLiveData: MutableLiveData<List<User>> = repository.usersLiveData

    fun getUsers() {
        viewModelScope.launch {
            repository.getUsers()
        }
    }
}