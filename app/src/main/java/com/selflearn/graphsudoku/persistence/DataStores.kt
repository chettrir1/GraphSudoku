package com.selflearn.graphsudoku.persistence

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.selflearn.graphsudoku.GameSettings
import com.selflearn.graphsudoku.Statistics
import java.io.InputStream
import java.io.OutputStream

internal val Context.settingsDataStore: DataStore<GameSettings> by dataStore(
    fileName = "game_settings.pb",
    serializer = GameSettingsSerializer
)

//helps read and write from input stream
private object GameSettingsSerializer : Serializer<GameSettings> {
    override val defaultValue: GameSettings
        get() = GameSettings.getDefaultInstance()


    override suspend fun readFrom(input: InputStream): GameSettings {
        try {
            return GameSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto. ", exception)
        }
    }

    override suspend fun writeTo(t: GameSettings, output: OutputStream) = t.writeTo(output)

}

internal val Context.statsDataStore: DataStore<Statistics> by dataStore(
    fileName = "user_statistics.pb",
    serializer = StatisticsSerializer
)

private object StatisticsSerializer : Serializer<Statistics> {
    override val defaultValue: Statistics
        get() = Statistics.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Statistics {
        try {
            return Statistics.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto. ", exception)
        }
    }

    override suspend fun writeTo(t: Statistics, output: OutputStream) = t.writeTo(output)
}