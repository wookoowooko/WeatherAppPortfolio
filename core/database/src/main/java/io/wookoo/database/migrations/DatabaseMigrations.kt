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
            db.execSQL("ALTER TABLE weekly_weather ADD COLUMN last_update INTEGER NOT NULL DEFAULT ${System.currentTimeMillis()}")
        }
    }

}
