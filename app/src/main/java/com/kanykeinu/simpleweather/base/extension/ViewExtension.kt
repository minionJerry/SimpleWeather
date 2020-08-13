package com.kanykeinu.simpleweather.base.extension

import android.view.View

fun View.hide(gone: Boolean = true) {
    visibility = if (gone) View.GONE else View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)