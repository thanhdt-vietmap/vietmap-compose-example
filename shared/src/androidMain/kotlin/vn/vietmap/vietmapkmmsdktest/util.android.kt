package vn.vietmap.vietmapkmmsdktest

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun getDefaultColorScheme(isDark: Boolean): ColorScheme {
    return darkColorScheme()
}



actual object Platform {
    actual val isAndroid: Boolean
        get() = true
    actual val isIos: Boolean
        get() = false
    actual val isDesktop: Boolean
        get() = false
    actual val isWeb: Boolean
        get() = false
}