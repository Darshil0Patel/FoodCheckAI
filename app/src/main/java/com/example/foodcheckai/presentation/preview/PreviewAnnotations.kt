package com.example.foodcheckai.presentation.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * Custom preview annotations for FoodCheckAI app
 *
 * These annotations provide reusable preview configurations to ensure
 * consistent preview setups across all composables while reducing boilerplate.
 */

/**
 * Standard light theme preview
 * Shows component in light mode with white background
 */
@Preview(
    name = "Light Mode",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
annotation class LightPreview

/**
 * Standard dark theme preview
 * Shows component in dark mode with dark background
 */
@Preview(
    name = "Dark Mode",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class DarkPreview

/**
 * Combined light and dark theme preview
 * Use this for most components to verify both theme variants
 */
@LightPreview
@DarkPreview
annotation class ThemePreview

/**
 * Phone-sized preview (default Android device)
 * Standard phone dimensions: 411dp x 891dp
 */
@Preview(
    name = "Phone",
    showBackground = true,
    device = "spec:width=411dp,height=891dp"
)
annotation class PhonePreview

/**
 * Tablet-sized preview
 * Useful for testing responsive layouts on larger screens
 * Standard tablet dimensions: 1280dp x 800dp
 */
@Preview(
    name = "Tablet",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
annotation class TabletPreview

/**
 * Landscape orientation preview
 * Shows component in landscape mode (rotated device)
 */
@Preview(
    name = "Landscape",
    showBackground = true,
    device = "spec:width=891dp,height=411dp",
    widthDp = 891,
    heightDp = 411
)
annotation class LandscapePreview

/**
 * Font scaling preview (large text)
 * Tests accessibility with 1.5x font scale for users with visual impairments
 * Helps identify text truncation and layout issues
 */
@Preview(
    name = "Large Font",
    showBackground = true,
    fontScale = 1.5f
)
annotation class FontScalePreview

/**
 * Small font scale preview
 * Tests minimum readable font size
 */
@Preview(
    name = "Small Font",
    showBackground = true,
    fontScale = 0.85f
)
annotation class SmallFontPreview

/**
 * Complete preview suite for important screens
 * Includes: Light theme, Dark theme, Tablet layout, Large font scale
 * Use this for main screens to ensure comprehensive coverage
 */
@ThemePreview
@TabletPreview
@FontScalePreview
annotation class CompletePreview

/**
 * Standard component preview (light + dark themes only)
 * Use this for most UI components
 * Provides good coverage without excessive preview count
 */
@ThemePreview
annotation class ComponentPreview
