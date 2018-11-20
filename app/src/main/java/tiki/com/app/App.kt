package tiki.com.app

import android.content.Context
import android.content.SharedPreferences
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import tiki.com.app.di.AppModules
import tiki.com.app.di.ApplicationComponent
import tiki.com.app.utils.AppUtils
import tiki.com.app.utils.ValidateUtils
import tiki.com.app.application.Configs
import tiki.com.app.di.DaggerApplicationComponent
import tiki.com.app.libs.utils.RxBus
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by ninhvanluyen on 1/29/18.
 */
class App : MultiDexApplication() {
    companion object {
        lateinit var app: App
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .appModules(AppModules(this))
                .build()

    }

    override fun onCreate() {
        super.onCreate()
        app = this
        component.inject(this)
        initializeTimber()
        Fresco.initialize(this)
    }

    private fun initializeTimber() {
        if (Configs.IS_DEBUG)
            Timber.plant(Timber.DebugTree())
    }



    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}