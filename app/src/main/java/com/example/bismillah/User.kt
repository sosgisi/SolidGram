package com.example.bismillah

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView

data class User(val name: String = "", val image: Int = 0, val caption: String = "") : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        if (image != null) {
            parcel.writeInt(image)
        }
        parcel.writeString(caption)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
