
package com.example.routineapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val OliveScheme = darkColorScheme(
    primary = Color(0xFF9CB380),
    onPrimary = Color(0xFF111312),
    secondary = Color(0xFFB8B8B8),
    surface = Color(0xFF141615),
    onSurface = Color(0xFFECECEC)
)

@Composable
fun RoutineTheme(dark: Boolean = true, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = OliveScheme,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
