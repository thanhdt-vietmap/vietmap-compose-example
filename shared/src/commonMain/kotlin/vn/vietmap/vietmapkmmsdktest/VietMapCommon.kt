package vn.vietmap.vietmapkmmsdktest

import androidx.compose.runtime.Composable
import vn.vietmap.vietmapcompose.compose.VietMapGLCompose
import vn.vietmap.vietmapcompose.core.OrnamentSettings

@Composable
fun VietMapCommon() {
    VietMapGLCompose (
        ornamentSettings = OrnamentSettings(
            isAttributionEnabled = false
        ),
        styleUri = "https://maps.vietmap.vn/api/maps/light/styles.json?apikey=YOUR_API_KEY_HERE") {
    }
}