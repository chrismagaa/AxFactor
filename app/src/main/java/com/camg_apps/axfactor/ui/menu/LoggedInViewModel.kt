package com.camg_apps.axfactor.ui.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.camg_apps.axfactor.data.AppRepository
import com.google.firebase.auth.FirebaseUser


class LoggedInViewModel: ViewModel() {
    private var authAppRepository: AppRepository? = null
     var userLiveData: MutableLiveData<FirebaseUser>? = null
     var loggedOutLiveData: MutableLiveData<Boolean>?  = null

    init {
        authAppRepository = AppRepository()
        userLiveData = authAppRepository!!.userLiveData
        loggedOutLiveData = authAppRepository!!.loggedOutLiveData
    }

    fun logOut() {
        authAppRepository!!.logOut()
    }


}