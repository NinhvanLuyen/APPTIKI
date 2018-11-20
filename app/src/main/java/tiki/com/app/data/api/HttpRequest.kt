package tiki.com.app.data.api

import tiki.com.app.data.api.responses.LoadMoreData
import io.reactivex.Single
import retrofit2.http.*

/**
 * Created by ninhvanluyen on 16/11/18.
 */
interface HttpRequest {

    @GET("38b790795722e7d7b1b5db051c5786e5/raw/63380022f5f0c9a100f51a1e30887ca494c3326e/keywords.json")
    fun getListTag(@QueryMap option: Map<String, String>): Single<Array<String>>

//    @GET("search/multi")
//    fun search(@QueryMap option: Map<String, String>): Single<SearchRes>

}