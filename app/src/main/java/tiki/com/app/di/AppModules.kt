package tiki.com.app.di

import android.app.Application
import android.content.SharedPreferences
import android.content.res.Resources
import android.preference.PreferenceManager
import tiki.com.app.data.api.ApiServicesImp
import tiki.com.app.data.api.HttpRequest
import tiki.com.app.data.local.LocalServicesImp
import tiki.com.app.domain.services.ApiService
import tiki.com.app.domain.services.LocalServices
import tiki.com.app.libs.Environment
import tiki.com.app.libs.UseCaseEnvironment
import tiki.com.app.libs.preferences.StringPreference
import tiki.com.app.libs.qualifers.ApiRetrofit
import tiki.com.app.application.Configs
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import tiki.com.app.domain.usecases.ShopUsecase
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import tiki.com.app.libs.utils.NullStringToEmptyAdapterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


/**
 * Created by ninhvanluyen on 16/11/18.
 */
@Module
open class AppModules(private val application: Application) {

    @Provides
    @Singleton
    fun provideEnvironment(gson: Gson,
                           resources: Resources,
                           shopUsecase: ShopUsecase) = Environment(gson,
            resources,
            shopUsecase,
            application.applicationContext)

    @Provides
    @Singleton
    fun provideResource() = application.resources

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().registerTypeAdapterFactory(NullStringToEmptyAdapterFactory()).create()

    }


    //
    @Provides
    @Singleton
    @Named("User")
    fun provideUserPreference(sharedPreferences: SharedPreferences) = StringPreference(sharedPreferences, "user")

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

//    @Provides
//    @Singleton
////    fun provideUserUseCase(useCaseEnvironment: UseCaseEnvironment) = ShopUsecase(useCaseEnvironment)


    @Provides
    @Singleton
    fun provideUseCaseEnvironment(apiServices: ApiService, localService: LocalServices) = UseCaseEnvironment(apiServices, localService)

    @Provides
    @Singleton
    fun provideApiService(httpRequest: HttpRequest): ApiService = ApiServicesImp(httpRequest)

    @Provides
    @Singleton
    fun provideLocalService(sharedPreferences: SharedPreferences, gson: Gson): LocalServices = LocalServicesImp(sharedPreferences, gson)

    @Provides
    @Singleton
    fun provideHttpRequest(retrofit: Retrofit): HttpRequest = retrofit.create(HttpRequest::class.java)

    @Provides
    @Singleton
    @ApiRetrofit
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Configs.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(cookieJar: JavaNetCookieJar, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        try {
            builder.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build()
                chain.proceed(request)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (Configs.IS_DEBUG)
            builder.addInterceptor(loggingInterceptor)
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }
        })
        val noSSLv3Factory = TLSSocketFactory()
        HttpsURLConnection.setDefaultSSLSocketFactory(noSSLv3Factory)
        builder.sslSocketFactory(noSSLv3Factory, trustAllCerts[0] as X509TrustManager)


        return builder
                .cookieJar(cookieJar)
                .connectTimeout(Configs.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Configs.READ_TIME_OUT, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideCookieJar(): JavaNetCookieJar = JavaNetCookieJar(CookieManager())


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    //provide usecase
    @Provides
    @Singleton
    fun privideUserUseCase(useCaseEnvironment: UseCaseEnvironment) = ShopUsecase(useCaseEnvironment)


}