import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST



private const val BASE_URL = "https://eppt.graciasgroup.com"
//private const val BASE_URL =
//    "https://android-kotlin-fun-mars-server.appspot.com"
//private const val BASE_URL =
//    "https://eppt.graciasgroup.com/api/sms"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface UserApiService {
//    @GET("send/?phone_number=0824019836&message=eppt verify -c 122234453 -v 4500")
//    @GET("photos")
    @GET("api/sms/send/base64?phone_number=+243825937168&message=ZXBwdCB2ZXJpZnkgLWMgMTIyMjM0NDUzIC12IDIwMDA")
    suspend fun sendUser(): String

/*
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("send")
    suspend fun sendUser(@Body userData: Map<String, String>): String;
     */
}

object UserApi {
    val retrofitService : UserApiService by lazy {retrofit.create(UserApiService::class.java)}
}
