package kz.ali.alarmclock.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alarm(
    val id: Int,
    var name: String? = null,
    var ringtone: String,
    var time: String,
    var days: List<Days>,
    var vibration: Boolean = true,
    var snooze: Snooze? = null,
    var isActive: Boolean = false
) : Parcelable {

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
        private var interval: Interval,
        private var repeat: Repeat
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