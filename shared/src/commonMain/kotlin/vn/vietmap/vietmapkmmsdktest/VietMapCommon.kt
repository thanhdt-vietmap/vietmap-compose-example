package vn.vietmap.vietmapkmmsdktest

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import vn.vietmap.vietmapcompose.compose.VietMapGLCompose

@Composable
fun VietMapCommon() {
    VietMapGLCompose (
        styleUri = "https://maps.vietmap.vn/api/maps/light/styles.json?apikey=YOUR_API_KEY_HERE") {
    }
}