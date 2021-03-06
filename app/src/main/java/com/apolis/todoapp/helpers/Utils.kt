package com.apolis.todoapp.helpers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT)
}

fun Context.d(message: String) {
    Log.d("ABC", message)
}

fun openActivity(context: Context, clazz: Class<*>, extras: Bundle?) {
    val intent = Intent(context, clazz)
    if(extras != null) {
        intent.putExtras(extras)
    }
    context.startActivity(intent)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}