package com.apolis.todoapp.helpers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

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