package tiki.com.app.libs

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import tiki.com.app.domain.usecases.ShopUsecase

/**
 * Created by ninhvanluyen on 16/11/18.
 */
data class Environment(val gson: Gson,
                       val resources: Resources,
                       val shopUsecase: ShopUsecase,
                       val context: Context)