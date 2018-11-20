package tiki.com.app.data.api

import tiki.com.app.application.ErrorCodes
import tiki.com.app.application.ErrorMessage
import tiki.com.app.domain.services.ApiService
import io.reactivex.Single
import tiki.com.app.data.api.responses.LoadMoreData
import tiki.com.app.libs.apierror.ApiError
import tiki.com.app.libs.exceptions.ApiException
import tiki.com.app.libs.tranforms.ApiTransformer
import tiki.com.app.utils.DeviceUtils
import io.reactivex.schedulers.Schedulers

/**
 * Created by ninhvanluyen on 16/11/18.
 */
class ApiServicesImp(val httpRequest: HttpRequest) : ApiService {
    override fun getListTag(): Single<Array<String>> {
        return Single.defer {
            if (!DeviceUtils.isNetworkAvailable()) {
                Single.error(ApiException(ApiError(ErrorCodes.NET_WORK_PROBLEM, ErrorMessage.NET_WORK_PROBLEM)))
            } else {
                val option = HashMap<String, String>()
                option.put("pagesize", "30")
                option.put("site", "stackoverflow")
                httpRequest.getListTag(option)
                        .subscribeOn(Schedulers.io())
            }
        }
    }


}