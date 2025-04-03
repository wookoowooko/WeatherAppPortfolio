package io.wookoo.network

import JvmUnitTestDemoAssetManager
import io.wookoo.domain.utils.AppResult
import io.wookoo.network.demo.DemoGeocodingImpl
import io.wookoo.network.dto.geocoding.GeocodingResponseDto
import io.wookoo.network.dto.geocoding.GeocodingSearchDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingSearchDto
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DeSerializationLocationTest {

    private lateinit var subject: DemoGeocodingImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        subject = DemoGeocodingImpl(
            ioDispatcher = testDispatcher,
            networkJson = Json { ignoreUnknownKeys = true },
            demoAssetManager = JvmUnitTestDemoAssetManager,
        )
    }

    @Test
    fun testDeserializationDemoGeocodingResults() = runTest(testDispatcher) {

        val expected = GeocodingResponseDto(
            results = listOf(
                GeocodingSearchDto(
                    geoNameId = 2950159,
                    name = "Berlin",
                    latitude = 52.52437,
                    longitude = 13.41053,
                    country = "Germany",
                    admin1 = "Berlin, Stadt",
                ),
                GeocodingSearchDto(
                    geoNameId = 1605651,
                    name = "Thailand",
                    latitude = 15.5,
                    longitude = 101.0,
                    country = "Thailand",
                    admin1 = null
                )
            )
        )

        val actual = subject.searchLocation(name = anyTestData())

        assertNotNull(actual)
        assertTrue(actual is AppResult.Success)
        assertEquals(expected, (actual as AppResult.Success).data)
    }

    @Test
    fun getInfoByGeoItemId() = runTest(testDispatcher) {
        val expected =
            GeocodingSearchDto(
                geoNameId = 2950159,
                name = "Berlin",
                latitude = 52.52437,
                longitude = 13.41053,
                country = "Germany",
                admin1 = "Land Berlin",
            )
        val actual = subject.getInfoByGeoItemId(geoItemId = anyTestData())

        assertNotNull(actual)
        assertTrue(actual is AppResult.Success)
        assertEquals(expected, (actual as AppResult.Success).data)
    }

    @Test
    fun getReversedSearchedLocation() = runTest(testDispatcher) {
        val expected =
            ReverseGeocodingResponseDto(
                geonames = listOf(
                    ReverseGeocodingSearchDto(
                        geoNameId = 5099133,
                        cityName = "Hoboken",
                        toponymName = "Hoboken",
                        countryName = "United States",
                        areaName = "New Jersey"
                    )
                )
            )
        val actual = subject.getReversedSearchedLocation(
            latitude = anyTestData(),
            longitude = anyTestData()
        )

        assertNotNull(actual)
        assertTrue(actual is AppResult.Success)
        assertEquals(expected, (actual as AppResult.Success).data)
    }

}

