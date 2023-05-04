package com.example.agroceylonapp.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class ACEditText(context:Context, attrs: AttributeSet): AppCompatEditText(context, attrs) {

    init{
        //call the function to apply the font to the components
        applyFont()
    }

    private fun applyFont(){

        //this is used to get the file from the assets folder and set it to the title textview
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Montserrat-Regular.ttf")
        setTypeface(typeface)
    }

}