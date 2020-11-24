package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*

fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = this.currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardOpen():Boolean {
    val point = Point()
    val screenSize = this.windowManager.defaultDisplay.getSize(point)
    val rect = Rect()
    this.window.decorView.getWindowVisibleDisplayFrame(rect)
    return point.y == rect.bottom
}

fun Activity.isKeyboardClosed():Boolean {
    val point = Point()
    val screenSize = this.windowManager.defaultDisplay.getSize(point)
    val rect = Rect()
    this.window.decorView.getWindowVisibleDisplayFrame(rect)
    return point.y - rect.bottom > 200
}