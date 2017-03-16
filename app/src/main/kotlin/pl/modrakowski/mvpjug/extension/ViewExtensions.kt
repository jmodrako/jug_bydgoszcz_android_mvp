package pl.modrakowski.mvpjug.extension

import android.view.View
import android.widget.TextView

fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View.click(block: (View) -> Unit) {
    setOnClickListener { block(it) }
}

fun TextView.clearError() {
    error = null
}
