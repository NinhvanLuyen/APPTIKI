package tiki.com.app.libs.qualifers

import tiki.com.app.libs.ActivityViewModel
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

/**
 * Created by ninhvanluyen on 16/11/18.
 */
@Inherited
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireActivityViewModel(val value: KClass<out ActivityViewModel>)
