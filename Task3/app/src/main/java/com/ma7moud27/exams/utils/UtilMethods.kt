package com.ma7moud27.exams.utils

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.ma7moud27.exams.R

object UtilMethods {
    fun showProgressbar(context: Context, text: String): AlertDialog =
        AlertDialog.Builder(context).let {
            it.setView(
                LayoutInflater
                    .from(context)
                    .inflate(R.layout.progress_bar_layout, null)
                    .apply {
                        findViewById<TextView>(R.id.loading_dialog_title)
                            .text = text
                    },
            )
            it.setCancelable(false)
            it.create()
        }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
    fun Editable.toInt(): Int = this.toString().toInt()
    fun Editable.toListChoices(): List<String> = this.toString().split("\n")
}
