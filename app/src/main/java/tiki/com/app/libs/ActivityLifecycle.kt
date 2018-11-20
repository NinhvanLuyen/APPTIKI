package tiki.com.app.libs

import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable

/**
 * Created by ninhvanluyen on 16/11/18.
 */
interface ActivityLifecycle {
    fun lifecycle(): Observable<ActivityEvent>
}