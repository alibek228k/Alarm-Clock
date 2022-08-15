package kz.ali.alarmclock.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alarm(
    private val id: Int,
    private var alarmName: String? = null,
    private var ringtone: String,
    private var alarmTime: String,
    private var alarmDays: List<Days>,
    private var vibration: Boolean = true,
    private var snooze: Snooze? = null,
    private var isActive: Boolean = false
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

        enum class Repeat{
            THREE,
            FIVE,
            FOREVER
        }
    }
}