package com.camg_apps.axfactor.data.model

import android.os.Parcelable
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Formula(
        var code_reference: String? = null,
        val description: String? = "",
        val color: Long? = 0,
        val materiales: List<Material>? = null
) : Parcelable
