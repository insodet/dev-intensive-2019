package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"):String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits):Date{
    this.time += units.time * value
    return this
}

fun Date.humanizeDiff(date: Date = Date()):String{
    val timeDiff = date.time - this.time
    val momentInPast = timeDiff >= 0
    val secondsDiff = (timeDiff / TimeUnits.SECOND.time).toInt().absoluteValue
    val minutesDiff = (timeDiff / TimeUnits.MINUTE.time).toInt().absoluteValue
    val hoursDiff = (timeDiff / TimeUnits.HOUR.time).toInt().absoluteValue
    val daysDiff = (timeDiff / TimeUnits.DAY.time).toInt().absoluteValue
    val getStringInPastOrFuture:(humanizeTime:String)-> String = { humanizeTime: String ->
        if (momentInPast) "$humanizeTime назад" else "через $humanizeTime"
    }
    return when{
        secondsDiff in 0..1 && momentInPast -> "только что"
        secondsDiff in 0..1 && !momentInPast -> getStringInPastOrFuture(TimeUnits.SECOND.plural(secondsDiff))
        secondsDiff in 1..45 -> getStringInPastOrFuture("несколько секунд")
        secondsDiff > 75 && minutesDiff <= 45 -> getStringInPastOrFuture(TimeUnits.MINUTE.plural(minutesDiff))
        minutesDiff in 45..75 -> getStringInPastOrFuture("час")
        minutesDiff > 75 && hoursDiff <= 22 -> getStringInPastOrFuture(TimeUnits.HOUR.plural(hoursDiff))
        hoursDiff in 22..26 -> getStringInPastOrFuture("день")
        hoursDiff > 26 && daysDiff <= 360 -> getStringInPastOrFuture(TimeUnits.DAY.plural(daysDiff))
        daysDiff > 360 && momentInPast -> "более года назад"
        else -> "более чем через год"
    }
}

enum class TimeUnits(val time: Long,
                    private val single: String,
                     private val plural1: String,
                     private val plural2: String) {
    SECOND(1000L, "секунду", "секунды", "секунд"),
    MINUTE(SECOND.time * 60, "минуту", "минуты", "минут"),
    HOUR(MINUTE.time * 60, "час", "часа", "часов"),
    DAY(HOUR.time * 24, "день", "дня", "дней");

    fun plural(value: Int): String {
        val lastNum = value % 10
        return when{
            value > 10 && value % 100 in 11..19 -> "$value $plural2"
            lastNum == 1 -> "$value $single"
            lastNum in 2..4 -> "$value $plural1"
            else -> "$value $plural2"
        }
    }

}