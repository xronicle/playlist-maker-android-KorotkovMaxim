package com.example.playlistmaker.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

fun <T> Context.startActivity(targetActivity: Class<T>) {
    val intent = Intent(this, targetActivity)

    if (this !is Activity) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    startActivity(intent)
}