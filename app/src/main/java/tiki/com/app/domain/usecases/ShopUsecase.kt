package tiki.com.app.domain.usecases

import tiki.com.app.domain.services.ApiService
import tiki.com.app.domain.services.LocalServices
import tiki.com.app.libs.UseCaseEnvironment

/**
 * Created by ninhvanluyen on 16/11/18.
 */
class ShopUsecase(useCaseEnvironment: UseCaseEnvironment) {
    private val apiServices: ApiService = useCaseEnvironment.apiServices
    private val localService: LocalServices = useCaseEnvironment.localService

    fun getListUser() = apiServices.getListTag().toObservable()

}
