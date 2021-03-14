package com.sahel.customloader

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat

class CustomLoad {
    lateinit var dialog: CustomDialog

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    fun show(context: Context): Dialog {
        return show(context, null)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    fun show(context: Context, title: CharSequence?): Dialog {

        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.loading_layout, null)
        val cp_title = view.findViewById<View>(R.id.cp_title) as TextView
        if (title != null) {
            cp_title.text = title
        }

        // Card Color
        val cp_cardview = view.findViewById<View>(R.id.cp_cardview) as CardView
        cp_cardview.setCardBackgroundColor(Color.parseColor("#70000000"))

        // Progress Bar Color
        val cp_pbar = view.findViewById<View>(R.id.cp_pbar) as ProgressBar
        setColorFilter(cp_pbar.indeterminateDrawable, ResourcesCompat.getColor(context.resources, R.color.colorPrimary, null))

        // Text Color
        cp_title.setTextColor(Color.WHITE)

        dialog = CustomDialog(context)
        dialog.setContentView(view)
        dialog.show()
        return dialog
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    class CustomDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
        init {
            // Set Semi-Transparent Color for Dialog Background



            window?.decorView?.rootView?.setBackgroundResource(R.color.transparent)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}