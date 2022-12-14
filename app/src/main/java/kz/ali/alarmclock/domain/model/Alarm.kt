package kz.ali.alarmclock.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

const val DEFAULT_VOLUME = 0.66667f

@Parcelize
data class Alarm(
    val id: Int,
    var nextTime: Calendar,
    var name: String? = null,
    var ringtone: Ringtone,
    var time: String,
    var days: List<Days>,
    var vibration: Vibration = Vibration(true, "some name or uri of vibration"),
    var snooze: Snooze? = null,
    var isActive: Boolean = false,
) : Parcelable {


    @Parcelize
    class Vibration(var isTurnedOn: Boolean, var vibrationUri: String) : Parcelable

    @Parcelize
    class Ringtone(
        var isTurnedOn: Boolean,
        var ringtoneUri: String,
        var volume: Float = DEFAULT_VOLUME,
    ) :
        Parcelable

    enum class Days {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    @Parcelize
    data class Snooze(
        var interval: Interval,
        var repeat: Repeat,
        var isTurnedOn: Boolean,
    ) : Parcelable {

        enum class Interval {
            FIVE,
            TEN,
            FIFTEEN,
            THIRTY
        }

        enum class Repeat {
            THREE,
            FIVE,
            FOREVER
        }
    }
}