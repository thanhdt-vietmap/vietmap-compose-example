package vn.vietmap.vietmapkmmsdktest


import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController

@Suppress("unused", "FunctionName") // called in Swift
fun MainViewController() = ComposeUIViewController {
    VietMapCommon()
}

@Composable
actual fun getDefaultColorScheme(isDark: Boolean): ColorScheme {
    return if (isDark) darkColorScheme() else lightColorScheme()
}

