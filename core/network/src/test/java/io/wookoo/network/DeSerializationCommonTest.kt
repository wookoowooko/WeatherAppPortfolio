package io.wookoo.network

import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingSearchDto
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DeSerializationCommonTest {

    private lateinit var json: Json

    @Test
    fun `must ignore unknown keys`() {

        json = Json { ignoreUnknownKeys = true }

        val expected = ReverseGeocodingResponseDto(
            geonames = listOf(
                ReverseGeocodingSearchDto(
                    geoNameId = 5099133,
                    cityName = "Hoboken",
                    toponymName = "Hoboken",
                    countryName = "United States",
                    areaName = "New Jersey",
                )
            )
        )

        val jsonString = """
            {
                "geonames": [
                    {
                        "geonameId": 5099133,
                        "name": "Hoboken",  
                        "adminName1": "New Jersey", 
                        "cityName": "Hoboken",
                        "toponymName": "Hoboken",
                        "countryName": "United States",
                        "areaName": "New Jersey",
                        "extraField": "Some extra value",
                        "ignoreMe": "I have no sense"
                    }
                ]
            }
        """

        val actual = json.decodeFromString<ReverseGeocodingResponseDto>(jsonString)
        assertEquals(expected, actual)
    }

    @Test
    fun `must error when find unknown key`() {
        json = Json { ignoreUnknownKeys = false }

        val jsonString = """
        {
            "geonames": [
                {
                    "geoNameId": 5099133,      
                    "name": "Hoboken",        
                    "cityName": "Hoboken",
                    "toponymName": "Hoboken",
                    "countryName": "United States",
                    "areaName": "New Jersey",
                    "extraField": "Some extra value" 
                }
            ]
        }
        """

        assertFailsWith<SerializationException> {
            json.decodeFromString<ReverseGeocodingResponseDto>(jsonString)
        }
    }
}
