package com.example.tabapplication.ui.main.adapter

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import androidx.core.net.toUri
import java.net.URL

data class Ingredient (
    var ing_name: String,
    var ing_image: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString().toString(),
        source.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel){
        writeString(ing_name)
        writeString(ing_image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ingredient> {
        override fun createFromParcel(parcel: Parcel): Ingredient {
            return Ingredient(parcel)
        }

        override fun newArray(size: Int): Array<Ingredient?> {
            return arrayOfNulls(size)
        }
    }
}