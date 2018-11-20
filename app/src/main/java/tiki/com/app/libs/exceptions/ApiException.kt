package tiki.com.app.libs.exceptions

import tiki.com.app.libs.apierror.ApiError

/**
 * Created by ninhvanluyen on 16/11/18.
 */
class ApiException(val apiError: ApiError) : RuntimeException()