package com.example.tabapplication.ui.main.adapter

import android.os.Parcel
import android.os.Parcelable

data class Word(
    var vocabulary: String,
    var meaning: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString().toString(),
        source.readString().toString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(vocabulary)
        writeString(meaning)
    }

}