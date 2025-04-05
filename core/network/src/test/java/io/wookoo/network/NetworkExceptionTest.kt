package io.wookoo.network

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.api.geocoding.GeocodingServiceImpl
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.geocoding.IGeocodingRetrofit
import io.wookoo.network.api.reversegeocoding.IReverseGeoCodingService
import io.wookoo.network.api.reversegeocoding.IReverseGeocodingRetrofit
import io.wookoo.network.api.reversegeocoding.ReverseGeocodingServiceImpl
import io.wookoo.network.api.weather.ForecastServiceImpl
import io.wookoo.network.api.weather.IForecastRetrofit
import io.wookoo.network.api.weather.IForecastService
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class NetworkExceptionTest {

    private val mockWebServer = MockWebServer()

    private lateinit var forecastRetrofit: IForecastRetrofit
    private lateinit var forecastService: IForecastService

    private lateinit var geoCodingService: IGeoCodingService
    private lateinit var geoCodingRetrofit: IGeocodingRetrofit

    private lateinit var reverseGeoCodingService: IReverseGeoCodingService
    private lateinit var reverseGeoCodingRetrofit: IReverseGeocodingRetrofit

    private lateinit var json: Json

    @Before
    fun setup() {
        json = Json { ignoreUnknownKeys = true }
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()

        forecastRetrofit = retrofit.create(IForecastRetrofit::class.java)
        forecastService = ForecastServiceImpl(forecastRetrofit)


        geoCodingRetrofit = retrofit.create(IGeocodingRetrofit::class.java)
        geoCodingService = GeocodingServiceImpl(geoCodingRetrofit)

        reverseGeoCodingRetrofit = retrofit.create(IReverseGeocodingRetrofit::class.java)
        reverseGeoCodingService = ReverseGeocodingServiceImpl(reverseGeoCodingRetrofit)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test 400 BAD REQUEST response for multiple services`() = runTest {

        mockWebServer.enqueue(MockResponse().setResponseCode(400))
        mockWebServer.enqueue(MockResponse().setResponseCode(400))
        mockWebServer.enqueue(MockResponse().setResponseCode(400))


        val forecastResult = forecastService.getCurrentWeather(anyTestData(), anyTestData(), anyTestData(), anyTestData(), anyTestData())
        val geoCodingResult = geoCodingService.searchLocation(anyTestData())
        val reverseGeoCodingResult =
            reverseGeoCodingService.getReversedSearchedLocation(anyTestData(), anyTestData())


        assertTrue(forecastResult is AppResult.Error && forecastResult.error == DataError.Remote.UNKNOWN)
        assertTrue(geoCodingResult is AppResult.Error && geoCodingResult.error == DataError.Remote.UNKNOWN)
        assertTrue(reverseGeoCodingResult is AppResult.Error && reverseGeoCodingResult.error == DataError.Remote.UNKNOWN)
    }


    @Test
    fun `test 408 REQUEST_TIMEOUT response`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(408))
        mockWebServer.enqueue(MockResponse().setResponseCode(408))
        mockWebServer.enqueue(MockResponse().setResponseCode(408))

        val forecastResult = forecastService.getCurrentWeather(anyTestData(), anyTestData(), anyTestData(), anyTestData(), anyTestData())
        val geoCodingResult = geoCodingService.searchLocation(anyTestData())
        val reverseGeoCodingResult =
            reverseGeoCodingService.getReversedSearchedLocation(anyTestData(), anyTestData())

        assertTrue(forecastResult is AppResult.Error && forecastResult.error == DataError.Remote.REQUEST_TIMEOUT)
        assertTrue(geoCodingResult is AppResult.Error && geoCodingResult.error == DataError.Remote.REQUEST_TIMEOUT)
        assertTrue(reverseGeoCodingResult is AppResult.Error && reverseGeoCodingResult.error == DataError.Remote.REQUEST_TIMEOUT)
    }


    @Test
    fun `test 429 TOO_MANY_REQUESTS response`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(429))
        mockWebServer.enqueue(MockResponse().setResponseCode(429))
        mockWebServer.enqueue(MockResponse().setResponseCode(429))

        val forecastResult = forecastService.getCurrentWeather(anyTestData(), anyTestData(), anyTestData(), anyTestData(), anyTestData())
        val geoCodingResult = geoCodingService.searchLocation(anyTestData())
        val reverseGeoCodingResult =
            reverseGeoCodingService.getReversedSearchedLocation(anyTestData(), anyTestData())


        assertTrue(forecastResult is AppResult.Error && forecastResult.error == DataError.Remote.TOO_MANY_REQUESTS)
        assertTrue(geoCodingResult is AppResult.Error && geoCodingResult.error == DataError.Remote.TOO_MANY_REQUESTS)
        assertTrue(reverseGeoCodingResult is AppResult.Error && reverseGeoCodingResult.error == DataError.Remote.TOO_MANY_REQUESTS)
    }


    @Test
    fun `test 500 SERVER ERROR response`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        val forecastResult = forecastService.getCurrentWeather(anyTestData(), anyTestData(), anyTestData(), anyTestData(), anyTestData())
        val geoCodingResult = geoCodingService.searchLocation(anyTestData())
        val reverseGeoCodingResult =
            reverseGeoCodingService.getReversedSearchedLocation(anyTestData(), anyTestData())


        assertTrue(forecastResult is AppResult.Error && forecastResult.error == DataError.Remote.SERVER)
        assertTrue(geoCodingResult is AppResult.Error && geoCodingResult.error == DataError.Remote.SERVER)
        assertTrue(reverseGeoCodingResult is AppResult.Error && reverseGeoCodingResult.error == DataError.Remote.SERVER)

    }

}
