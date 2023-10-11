package com.on.callapps.data

import java.io.Serializable

data class Contact(
    val imgProfile: Int,
    val name: String? = null,
    val number: String? = null,
    val imgOk: Int,
    val rate:String? = null
):Serializable