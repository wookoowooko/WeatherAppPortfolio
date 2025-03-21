package io.wookoo.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal object DatabaseMigrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE weekly_weather ADD COLUMN latitude REAL NOT NULL DEFAULT 0.0")
            db.execSQL("ALTER TABLE weekly_weather ADD COLUMN longitude REAL NOT NULL DEFAULT 0.0")
        }
    }
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE current_weather ADD COLUMN latitude REAL NOT NULL DEFAULT 0.0")
            db.execSQL("ALTER TABLE current_weather ADD COLUMN longitude REAL NOT NULL DEFAULT 0.0")
        }
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE weekly_weather ADD COLUMN city_name TEXT NOT NULL DEFAULT ''")
        }
    }

    val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "ALTER TABLE weekly_weather ADD COLUMN last_update INTEGER NOT NULL DEFAULT ${System.currentTimeMillis()}"
            )
        }
    }

    val MIGRATION_5_6 = object : Migration(5, 6) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
            CREATE TABLE geo_entity_new (
                geo_name_id INTEGER PRIMARY KEY NOT NULL,
                city_name TEXT NOT NULL,
                country_name TEXT NOT NULL
            )
                """.trimIndent()
            )

            db.execSQL(
                """
            INSERT INTO geo_entity_new (geo_name_id, city_name, country_name)
            SELECT geo_name_id, city_name, country_name
            FROM geo_entity
                """.trimIndent()
            )

            db.execSQL("DROP TABLE geo_entity")
            db.execSQL("ALTER TABLE geo_entity_new RENAME TO geo_entity")
        }
    }

    val MIGRATION_6_7 = object : Migration(6, 7) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Создаем новую таблицу без latitude и longitude
            db.execSQL(
                """
            CREATE TABLE weekly_weather_new (
                geo_name_id INTEGER PRIMARY KEY NOT NULL,
                city_name TEXT NOT NULL,
                is_day INTEGER NOT NULL,
                time TEXT NOT NULL,
                weather_code TEXT NOT NULL,
                temperature_2m_max TEXT NOT NULL,
                temperature_2m_min TEXT NOT NULL,
                apparent_temperature_max TEXT NOT NULL,
                apparent_temperature_min TEXT NOT NULL,
                sunrise TEXT NOT NULL,
                sunset TEXT NOT NULL,
                daylight_duration TEXT NOT NULL,
                sunshine_duration TEXT NOT NULL,
                uv_index_max TEXT NOT NULL,
                precipitation_sum TEXT NOT NULL,
                rain_sum TEXT NOT NULL,
                showers_sum TEXT NOT NULL,
                snowfall_sum TEXT NOT NULL,
                precipitation_probability_max TEXT NOT NULL,
                wind_speed_10m_max TEXT NOT NULL,
                wind_gusts_10m_max TEXT NOT NULL,
                wind_direction_10m_dominant TEXT NOT NULL,
                last_update INTEGER NOT NULL
            )
                """.trimIndent()
            )

            // Копируем данные (исключаем latitude и longitude)
            db.execSQL(
                """
            INSERT INTO weekly_weather_new 
            SELECT 
                geo_name_id,
                city_name,
                is_day,
                time,
                weather_code,
                temperature_2m_max,
                temperature_2m_min,
                apparent_temperature_max,
                apparent_temperature_min,
                sunrise,
                sunset,
                daylight_duration,
                sunshine_duration,
                uv_index_max,
                precipitation_sum,
                rain_sum,
                showers_sum,
                snowfall_sum,
                precipitation_probability_max,
                wind_speed_10m_max,
                wind_gusts_10m_max,
                wind_direction_10m_dominant,
                last_update
            FROM weekly_weather
                """.trimIndent()
            )

            // Удаляем старую таблицу и переименовываем новую
            db.execSQL("DROP TABLE weekly_weather")
            db.execSQL("ALTER TABLE weekly_weather_new RENAME TO weekly_weather")
        }
    }

    val MIGRATION_7_8 = object : Migration(7, 8) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "ALTER TABLE current_weather ADD COLUMN last_update INTEGER NOT NULL DEFAULT ${System.currentTimeMillis()}"
            )
        }
    }

    val MIGRATION_8_9 = object : Migration(8, 9) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("DROP INDEX IF EXISTS index_current_weather_currentId") // Удаляем старый индекс

            db.execSQL(
                """ 
            CREATE TABLE current_weather_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                currentId INTEGER NOT NULL,
                time INTEGER NOT NULL,
                temperature_2m REAL NOT NULL,
                relative_humidity_2m INTEGER NOT NULL,
                apparent_temperature REAL NOT NULL,
                is_day INTEGER NOT NULL,
                precipitation REAL NOT NULL,
                rain REAL NOT NULL,
                showers REAL NOT NULL,
                snowfall REAL NOT NULL,
                cloud_cover INTEGER NOT NULL,
                pressure_msl REAL NOT NULL,
                wind_direction_10m INTEGER NOT NULL,
                wind_speed_10m REAL NOT NULL,
                wind_gusts_10m REAL NOT NULL,
                weather_code INTEGER NOT NULL,
                last_update INTEGER NOT NULL,
                FOREIGN KEY(currentId) REFERENCES geo_entity(geo_name_id) ON DELETE CASCADE
            )
                """.trimIndent()
            )

            db.execSQL(
                """
            INSERT INTO current_weather_new 
            SELECT 
                id,
                currentId,
                time,
                temperature_2m,
                relative_humidity_2m,
                apparent_temperature,
                is_day,
                precipitation,
                rain,
                showers,
                snowfall,
                cloud_cover,
                pressure_msl,
                wind_direction_10m,
                wind_speed_10m,
                wind_gusts_10m,
                weather_code,
                last_update
            FROM current_weather
                """.trimIndent()
            )

            db.execSQL("DROP TABLE current_weather")
            db.execSQL("ALTER TABLE current_weather_new RENAME TO current_weather")

            // Повторно создаем индекс
            db.execSQL("CREATE INDEX index_current_weather_currentId ON current_weather(currentId)")
        }
    }

    val MIGRATION_9_10 = object : Migration(9, 10) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE geo_entity ADD COLUMN utc_offset_seconds INTEGER NOT NULL DEFAULT 0")
        }
    }
    val MIGRATION_10_11 = object : Migration(10, 11) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE weekly_weather ADD COLUMN utc_offset_seconds INTEGER NOT NULL DEFAULT 0")
        }
    }
}
