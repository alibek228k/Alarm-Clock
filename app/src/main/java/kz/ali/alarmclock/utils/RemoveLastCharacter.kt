package kz.ali.alarmclock.utils

fun String.removeLastCharacter(): String {
    var str = this
    if (str[str.length - 1] == ',') {
        str = str.dropLast(1)
        return str
    }
    return str
}