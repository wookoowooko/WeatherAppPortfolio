package io.wookoo.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserSettingsSerializer @Inject constructor() : Serializer<UserSettings> {
    override val defaultValue: UserSettings
        get() = UserSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserSettings {
        return try {
            UserSettings.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: UserSettings, output: OutputStream) {
        t.writeTo(output)
    }
}
