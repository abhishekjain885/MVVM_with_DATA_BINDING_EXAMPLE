package com.example.apple.minidemo.model

import android.databinding.BaseObservable
import android.os.Parcel
import android.os.Parcelable


class SourceItem() : BaseObservable(), Parcelable {

    var id: String? = null

    var name: String? = null

    var description: String? = null

    var url: String? = null

    var category: String? = null

    var language: String? = null

    var country: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        description = parcel.readString()
        url = parcel.readString()
        category = parcel.readString()
        language = parcel.readString()
        country = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(category)
        parcel.writeString(language)
        parcel.writeString(country)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SourceItem> {
        override fun createFromParcel(parcel: Parcel): SourceItem {
            return SourceItem(parcel)
        }

        override fun newArray(size: Int): Array<SourceItem?> {
            return arrayOfNulls(size)
        }
    }


}
