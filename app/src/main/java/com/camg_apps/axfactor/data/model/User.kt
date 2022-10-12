package com.camg_apps.axfactor.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val nombre: String? = "",
    val correo: String? = "",
    val laboratorio: String? = "",
) : Parcelable
