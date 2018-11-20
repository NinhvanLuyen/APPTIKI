package tiki.com.app.data.api.responses

import tiki.com.app.application.Constants

/**
 * Created by ninhvanluyen on 16/11/18.
 */
class LoadMoreData<T> {
    private var items:ArrayList<T>  = arrayListOf<T>()
    private var quota_max = 0
    private var quota_remaining = 0
    private var has_more = false
    private var error_id = 0
    private var error_message = ""
    private var error_name = ""
    fun getHasMore() = has_more
    fun getDatas() = items
    fun getErrorID() = error_id
    fun getErrors(): String = error_message

}