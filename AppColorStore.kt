package com.example.newsapp

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("color_prefs")

class AppColorStore(private val context: Context) {

    private val TOP_BAR_COLOR = intPreferencesKey("top_bar_color")
    private val BACKGROUND_COLOR = intPreferencesKey("background_color")
    private val CARD_COLOR = intPreferencesKey("card_color")
    private val TEXT_COLOR = intPreferencesKey("text_color")

    val topBarColor: Flow<Color> = context.dataStore.data.map {
        Color(it[TOP_BAR_COLOR] ?: Color(0xFF2196F3).toArgb())
    }

    val backgroundColor: Flow<Color> = context.dataStore.data.map {
        Color(it[BACKGROUND_COLOR] ?: Color.White.toArgb())
    }

    val cardColor: Flow<Color> = context.dataStore.data.map {
        Color(it[CARD_COLOR] ?: Color(0xFFF1F1F1).toArgb())
    }

    val textColor: Flow<Color> = context.dataStore.data.map {
        Color(it[TEXT_COLOR] ?: Color.Black.toArgb())
    }

    suspend fun saveColors(topBar: Color, background: Color, card: Color, text: Color) {
        context.dataStore.edit { prefs ->
            prefs[TOP_BAR_COLOR] = topBar.toArgb()
            prefs[BACKGROUND_COLOR] = background.toArgb()
            prefs[CARD_COLOR] = card.toArgb()
            prefs[TEXT_COLOR] = text.toArgb()
        }
    }
}
