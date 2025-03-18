package io.wookoo.database.migrations

import androidx.room.DeleteColumn
import androidx.room.migration.AutoMigrationSpec

internal object DatabaseMigrations {

    @DeleteColumn(
        tableName = "weekly_weather",
        columnName = "id",
    )
    class Schema2to3 : AutoMigrationSpec
}
