package com.pd.photo_of_the_day_nasa.view.recycler

import android.os.Parcel
import android.os.Parcelable
import com.pd.photo_of_the_day_nasa.TYPE_BUY


data class Data(
    val label: String? = "Buy",
    val description: String? = "Description",
    val type: Int = TYPE_BUY, // тип ячейки
    var weight: Int=0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(label)
        parcel.writeString(description)
        parcel.writeInt(type)
        parcel.writeInt(weight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}

