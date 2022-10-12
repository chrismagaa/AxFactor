package com.camg_apps.axfactor.ui.main.login

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camg_apps.axfactor.data.AppRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class LoginViewModel(

): ViewModel() {
    private var appRepository: AppRepository? = null
    var userLiveData: MutableLiveData<FirebaseUser>

    init {
        appRepository =  AppRepository()
        userLiveData = appRepository!!.userLiveData
    }

    fun login(email: String?, password: String?, activity: Activity) {
        viewModelScope.launch {
            appRepository!!.login(email, password, activity)
        }
    }


}