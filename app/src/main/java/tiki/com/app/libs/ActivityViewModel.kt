package tiki.com.app.libs

import android.content.Intent
import tiki.com.app.libs.apierror.ApiError
import tiki.com.app.application.Constants
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * Created by ninhvanluyen on 16/11/18.
 */
open class ActivityViewModel{

    private val viewChange: PublishSubject<tiki.com.app.libs.ActivityLifecycle> = PublishSubject.create()
    private val view: Observable<tiki.com.app.libs.ActivityLifecycle> = viewChange.filter { view -> view != noView }
    private val activityResult: PublishSubject<ActivityResult> = PublishSubject.create()
    private val activityResultPermission: PublishSubject<ActivityResultPermission> = PublishSubject.create()
    private val intent: PublishSubject<Intent> = PublishSubject.create()

    private val noView: tiki.com.app.libs.ActivityLifecycle = ActivityLifecycle()

    private class ActivityLifecycle : tiki.com.app.libs.ActivityLifecycle {
        override fun lifecycle(): Observable<ActivityEvent> {
            return Completable.complete().toObservable()
        }
    }


    fun activityResult(activityResult: ActivityResult) {
        this.activityResult.onNext(activityResult)
    }
    fun activityResultPermision(activityResult: ActivityResultPermission) {
        this.activityResultPermission.onNext(activityResult)
    }

    fun intent(intent: Intent) {
        this.intent.onNext(intent)
    }

    fun onCreate() {
        dropView()
    }

    fun onResume(view: tiki.com.app.libs.ActivityLifecycle) {
        onTakeView(view)
    }

    fun onPause() {
        dropView()
    }

    fun onDestroy() {
        viewChange.onComplete()
    }

    private fun dropView() {
        viewChange.onNext(noView)
    }

    private fun onTakeView(view: tiki.com.app.libs.ActivityLifecycle) {
        viewChange.onNext(view)
    }

    protected fun activityResult(): Observable<ActivityResult> {
        return activityResult
    }
    protected fun activityResultPermission():Observable<ActivityResultPermission> =activityResultPermission

    protected fun intent(): Observable<Intent> {
        return intent
    }

    fun <T> bindTolifecycle(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.takeUntil<T> {
                view.switchMap { v -> v.lifecycle().map { e -> Pair(v, e) } }
                        .filter { ve -> isFinished(ve.first, ve.second) }
            }
        }
    }

    private fun isFinished(view: tiki.com.app.libs.ActivityLifecycle, event: ActivityEvent): Boolean {
        if (view is BaseActivity<*>) {
            return event == ActivityEvent.DESTROY && view.isFinishing
        }
        return event == ActivityEvent.DESTROY
    }

    protected var apiError = BehaviorSubject.create<ApiError>()
    fun errorDialogShowed() {
        apiError.onNext(ApiError(0, Constants.EMPTY))
    }

}