package com.camg_apps.axfactor.data.model

import android.os.Parcelable
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Formula(
        var codigo: String? = null,
        val armadora: String? = "",
        val linea: String? = "",
        val color: Long? = 0,
        val materiales: List<Material>? = null
) : Parcelable
