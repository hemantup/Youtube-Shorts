import android.os.Parcel
import android.os.Parcelable

data class VideoData(
    val channelName: String?,
    val videoUrl: String?,
    val channelImage: String?,
    val videoDescription: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(channelName)
        parcel.writeString(videoUrl)
        parcel.writeString(channelImage)
        parcel.writeString(videoDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoData> {
        override fun createFromParcel(parcel: Parcel): VideoData {
            return VideoData(parcel)
        }

        override fun newArray(size: Int): Array<VideoData?> {
            return arrayOfNulls(size)
        }
    }
}
