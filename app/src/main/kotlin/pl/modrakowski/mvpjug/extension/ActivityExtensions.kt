package pl.modrakowski.mvpjug.extension

import android.app.Activity
import android.content.Intent
import android.os.Looper
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.widget.Toast

fun Activity.onUi(block: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        block()
    } else {
        runOnUiThread(block)
    }
}

inline fun <reified T : Activity> Activity.launchActivity() {
    startActivity(Intent(this, T::class.java))
    finish()
}

fun Activity.dimenPixel(@DimenRes resId: Int): Int = resources.getDimensionPixelOffset(resId)
fun Activity.color(@ColorRes resId: Int): Int = ContextCompat.getColor(this, resId)
fun Activity.string(@StringRes resId: Int): String = resources.getString(resId)
fun Activity.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

