package tiki.com.app.domain.services

import tiki.com.app.data.api.responses.LoadMoreData
import io.reactivex.Single

/**
 * Created by ninhvanluyen on 16/11/18.
 */
interface ApiService {
    fun getListTag(): Single<Array<String>>
}