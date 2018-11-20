package tiki.com.app.libs

import tiki.com.app.domain.services.ApiService
import tiki.com.app.domain.services.LocalServices

/**
 * Created by ninhvanluyen on 16/11/18.
 */
class UseCaseEnvironment(val apiServices: ApiService,
                         val localService: LocalServices)