package com.example.videostatus.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


object DateTimeUtil {
    private const val timeZone = "UTC"
    private var locale = Locale.getDefault()
    private const val TAG = "DateTimeUtils"
    private const val DATEFORMATE = "yyyyMMddHHmmss"

    enum class DateTimeUnits {

        DAYS, SECONDS, MINUTES, HOURS, MILLISECONDS

    }

    fun getTimeInMillis() = System.currentTimeMillis().toString()


    fun getConvertedTime(fromFormat: String, format: String, dateString: String): String {
        var formattedDate = dateString
        val fromDateFormat = SimpleDateFormat(fromFormat, Locale.US)

        val toDateFormat = SimpleDateFormat(format, Locale.US)

        try {
            val myDate = fromDateFormat.parse(dateString) ?: Date()
            formattedDate = toDateFormat.format(myDate.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formattedDate
    }


    /*
     *  This method will check if given time is back then current last time
     */
    private fun isTimeStampIsPast(time: Long?, mLastTimeStamp: Long?): Boolean {
        try {
            val lastCalender = Calendar.getInstance()
            lastCalender.timeInMillis = mLastTimeStamp!!

            val currentCalender = Calendar.getInstance()
            currentCalender.timeInMillis = time!!

            if (currentCalender.before(lastCalender)) {
                return true
            }
        } catch (e: Exception) {
            JLog.e(TAG, e.printStackTrace().toString())
        }
        return false
    }


    private fun getFormattedDate(timeInmillies: Long, format: String): String {
        val mCalendar = Calendar.getInstance()
        mCalendar.timeInMillis = timeInmillies
        return getFormattedDate(mCalendar, format)
    }

    private fun getFormattedDate(calToDate: Calendar, format: String): String {
        return getFormat(format).format(calToDate.time)
    }

    private fun getFormat(format: String): SimpleDateFormat {
        val mDateFormat = SimpleDateFormat(format, locale)
        mDateFormat.timeZone = TimeZone.getTimeZone(timeZone)
        return mDateFormat
    }

    private fun getFormattedDate(date: Date, format: String): String {
        return getFormat(format).format(date)
    }

    @Throws(Exception::class)
    private fun convertDateFormat(dateStr: String?, fromFormat: String, toFormat: String): String? {
        if (dateStr == null || dateStr.isEmpty()) {
            return null
        }
        val fromFormatter = getFormat(fromFormat)
        val toFormatter = getFormat(toFormat)
        val date: Date?
        date = fromFormatter.parse(dateStr)
        return toFormatter.format(date!!)
    }

    private fun getDateTimeFlow(timeInMillis: Long, dateFormat: String): String {
        var dateResult = ""
        if (isToday(timeInMillis)) {
            dateResult = "Today"
        } else if (isYesterday(timeInMillis)) {
            dateResult = "Yesterday"
        } else {
            dateResult = getFormat(dateFormat).format(getDateTimeInMillis(timeInMillis))
        }
        return dateResult
    }

    private fun getDateTimeInMillis(timeInMillis1: Long): Date {
        val cal1 = Calendar.getInstance()
        if (timeInMillis1 > 0) {
            cal1.timeInMillis = timeInMillis1
        }
        return cal1.time
    }

    @Throws(Exception::class)
    private fun getTimeInMillis(dateString: String, format: String): Long {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        val date = dateFormat.parse(dateString)
        return date.time
    }


    private fun isToday(whenInMillis1: Long): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.timeInMillis = whenInMillis1
        val cal2 = Calendar.getInstance()
        return isSameDay(cal1, cal2)
    }

    private fun isYesterday(whenInMillis1: Long): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.timeInMillis = whenInMillis1
        val cal2 = Calendar.getInstance()
        cal2.add(Calendar.DAY_OF_YEAR, -1) // yesterday
        return isSameDay(cal1, cal2)
    }

    private fun isSameDay(date1: Date?, date2: Date?): Boolean {
        if (date1 == null || date2 == null) {
            return false
        }
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2
        return isSameDay(cal1, cal2)
    }

    private fun isSameDay(timeInMillis1: Long, timeInMillis2: Long): Boolean {
        if (timeInMillis1 == 0L || timeInMillis2 == 0L) {
            return false
        }
        val cal1 = Calendar.getInstance()
        cal1.timeInMillis = timeInMillis1
        val cal2 = Calendar.getInstance()
        cal2.timeInMillis = timeInMillis2
        return isSameDay(cal1, cal2)
    }

    fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) === cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) === cal2.get(
            Calendar.DAY_OF_YEAR
        )
    }

    fun getDateDiff(timeInMillis1: Long, timeInMillis2: Long, dateDiff: DateTimeUnits): Int {
        val date1 = Date(timeInMillis1)
        val date2 = Date(timeInMillis2)
        return getDateDiff(date1, date2, dateDiff)
    }

    fun getDateDiff(nowDate: Date, oldDate: Date, dateDiff: DateTimeUnits): Int {
        val diffInMs = nowDate.time - oldDate.time
        val days = TimeUnit.MILLISECONDS.toDays(diffInMs)
        val hours =
            (TimeUnit.MILLISECONDS.toHours(diffInMs) - TimeUnit.DAYS.toHours(days))
        val minutes = (TimeUnit.MILLISECONDS.toMinutes(diffInMs) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(diffInMs)
        ))
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMs)
        when (dateDiff) {
            DateTimeUnits.DAYS -> return days.toInt()
            DateTimeUnits.SECONDS -> return seconds.toInt()
            DateTimeUnits.MINUTES -> return minutes.toInt()
            DateTimeUnits.HOURS -> return hours.toInt()
            DateTimeUnits.MILLISECONDS -> return diffInMs.toInt()
            else -> return diffInMs.toInt()
        }
    }

    // return current TimeStamp
    fun getCurrentTimeStamp(): String {
        val dateFormat = SimpleDateFormat(DATEFORMATE, locale)
        return dateFormat.format(Date())
    }


}