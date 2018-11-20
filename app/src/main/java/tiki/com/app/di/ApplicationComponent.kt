package tiki.com.app.di

import tiki.com.app.libs.Environment
//import tiki.com.app.services.ConnectionListener
import dagger.Component
import tiki.com.app.App
import javax.inject.Singleton

/**
 * Created by ninhvanluyen on 16/11/18.
 */
@Singleton
@Component(modules = arrayOf(AppModules::class))
interface ApplicationComponent {
    fun environment(): Environment
    fun inject(app: App)
//    fun inject(listener: ConnectionListener)
}