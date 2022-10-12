package com.camg_apps.axfactor.data.model

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Material(
        var codigo: String? = "",
        var gramos: Double? = 0.0
) : Parcelable