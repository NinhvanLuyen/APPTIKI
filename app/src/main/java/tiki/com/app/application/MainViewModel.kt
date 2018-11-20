package tiki.com.app.application

import android.databinding.ObservableField
import android.view.View
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import tiki.com.app.R
import tiki.com.app.domain.usecases.ShopUsecase
import tiki.com.app.libs.ActivityViewModel
import tiki.com.app.libs.Environment
import tiki.com.app.libs.tranforms.Transformers
import timber.log.Timber
import java.util.*

interface MainViewModel {
    interface Output {
        fun renderData(): Observable<MutableList<Pair<String, Int>>>
    }

    class Data {
        val showLoading = ObservableField<Boolean>(false)
        val notfound = ObservableField<Boolean>(false)
        val loadDataError = ObservableField<Boolean>(false)
        var errorMessage = ObservableField<String>()

    }

    interface Errors {
        fun showErrorMessage(): Observable<String>
    }


    class ViewModel(environment: Environment) : ActivityViewModel(), Output, Errors {
        var outPut = this
        var error = this
        var data = Data()
        private var renderData = BehaviorSubject.create<MutableList<Pair<String, Int>>>()
        override fun renderData(): Observable<MutableList<Pair<String, Int>>> = renderData
        private var shopUsecase: ShopUsecase = environment.shopUsecase
        private var rnd = Random()
        private var backgrounds = arrayListOf<Int>(R.drawable.bg_blue, R.drawable.bg_brown, R.drawable.bg_green, R.drawable.bg_red, R.drawable.bg_cyan)
        private lateinit var loadData: Observable<MutableList<Pair<String, Int>>>

        init {
            data.showLoading.set(true)
            loadData = shopUsecase.getListUser()
                    .compose(Transformers.pipeApiErrorTo(apiError))
                    .compose(bindTolifecycle())
                    .map {
                        var ls = mutableListOf<Pair<String, Int>>()
                        for (name in it) {
                            ls.add(Pair(name, backgrounds[rnd.nextInt(backgrounds.size)]))
                        }
                        ls
                    }
                    .doOnNext {
                        data.loadDataError.set(false)
                        data.showLoading.set(false)
                        renderData.onNext(it)

                    }
            loadData.subscribe()
        }


        fun retry(v: View) {
            data.showLoading.set(true)
            data.loadDataError.set(false)
            loadData.subscribe()
        }

        fun noAction(v: View) {

        }

        override fun showErrorMessage(): Observable<String> = apiError
                .map {
                    Timber.e("EROOR")
                    data.loadDataError.set(true)
                    data.showLoading.set(false)
                    data.errorMessage.set(it.errorMessage)
                    it.errorMessage
                }
    }


}