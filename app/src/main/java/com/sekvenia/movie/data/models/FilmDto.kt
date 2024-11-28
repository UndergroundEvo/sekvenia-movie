package com.sekvenia.movie.data.models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


/*
* https://medium.com/@jskh1999/passing-objects-between-an-activity-and-fragments-41dde2f5b48a
* можно было через safeArgs еще передавать данные между фрагментами
*/

data class FilmDto(
    val description: String,
    val genres: List<String>,
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String? = "https://st.kp.yandex.net/images/no-poster.gif",
    @SerializedName("localized_name")
    val localizedName: String,
    val name: String,
    val rating: Double? = null,
    val year: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readInt()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeStringList(genres)
        parcel.writeInt(id)
        parcel.writeString(imageUrl)
        parcel.writeString(localizedName)
        parcel.writeString(name)
        parcel.writeValue(rating)
        parcel.writeInt(year)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<FilmDto> {
        override fun createFromParcel(parcel: Parcel): FilmDto = FilmDto(parcel)
        override fun newArray(size: Int): Array<FilmDto?> = arrayOfNulls(size)
    }
}