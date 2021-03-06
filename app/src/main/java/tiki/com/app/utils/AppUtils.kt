package tiki.com.app.utils

import android.app.Activity
import android.content.Context
import android.os.Environment
import tiki.com.app.App
import tiki.com.app.application.Constants
import tiki.com.app.application.PreferencesKeys
import android.util.DisplayMetrics
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by ninhvanluyen on 16/11/18.
 */
object AppUtils {
    /**
     * Get facebook app id of this app
     *
     * @return
     */
    fun getFacebookAppID(): String {
        return App.app.sharedPreferences.getString(PreferencesKeys.FACEBOOK_APP_ID, Constants.EMPTY)
    }

    fun convertLongToStringDate(date: Long): String {
        var fm = SimpleDateFormat("dd-MM-yyyy")
        var calendar =Calendar.getInstance()
        calendar.time = Date(date)
        calendar.add(Calendar.SECOND,date.toInt())
        var date = calendar.time
        return fm.format(date)
    }

    fun isFHD(context: Activity) {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        Timber.e("width_screen is $width")
        Constants.isFHD = width >= 1080
    }
}