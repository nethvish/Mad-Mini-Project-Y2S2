package com.example.agroceylon.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class ACRadioButton(context: Context, attributeSet: AttributeSet): AppCompatRadioButton(context, attributeSet) {

    init {
        applyFonts()
    }
    private fun applyFonts(){
        //this is use to get the file from asset folder and set it to the title
        val typeFace : Typeface = Typeface.createFromAsset(context.assets,"Montserrat-Bold.ttf")
        typeface = typeFace
    }


}