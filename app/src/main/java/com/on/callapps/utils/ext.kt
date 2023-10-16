package com.on.callapps.utils

import android.widget.ImageView

inline fun String.Companion.format(format: String, vararg args: Any?): String = java.lang.String.format(format, *args)