package vn.vietmap.vietmapkmmsdktest.demos

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import vn.vietmap.vietmapcompose.compose.VietMapGLCompose
import vn.vietmap.vietmapcompose.compose.layer.Anchor
import vn.vietmap.vietmapcompose.compose.layer.LineLayer
import vn.vietmap.vietmapcompose.compose.rememberCameraState
import vn.vietmap.vietmapcompose.compose.rememberStyleState
import vn.vietmap.vietmapcompose.compose.source.rememberGeoJsonSource
import vn.vietmap.vietmapcompose.core.CameraPosition
import vn.vietmap.vietmapkmmsdktest.DEFAULT_STYLE
import vn.vietmap.vietmapkmmsdktest.Demo
import vn.vietmap.vietmapkmmsdktest.DemoMapControls
import vn.vietmap.vietmapkmmsdktest.DemoOrnamentSettings
import vn.vietmap.vietmapkmmsdktest.DemoScaffold
import vn.vietmap.vietmapcompose.expressions.dsl.const
import vn.vietmap.vietmapcompose.expressions.dsl.exponential
import vn.vietmap.vietmapcompose.expressions.dsl.interpolate
import vn.vietmap.vietmapcompose.expressions.dsl.zoom
import vn.vietmap.vietmapcompose.expressions.value.LineCap
import vn.vietmap.vietmapcompose.expressions.value.LineJoin
import io.github.dellisd.spatialk.geojson.Position

private const val ROUTES_FILE = "files/data/amtrak_routes.geojson"

private val US = Position(latitude = 46.336, longitude = -96.205)

object AnimatedLayerDemo : Demo {
  override val name = "Animated layer"
  override val description = "Change layer properties at runtime."

  @Composable
  override fun Component(navigateUp: () -> Unit) {
    DemoScaffold(this, navigateUp) {
      val cameraState = rememberCameraState(firstPosition = CameraPosition(target = US, zoom = 2.0))
      val styleState = rememberStyleState()

      Box(modifier = Modifier.fillMaxSize()) {
        VietMapGLCompose(
          styleUri = DEFAULT_STYLE,
          cameraState = cameraState,
          styleState = styleState,
          ornamentSettings = DemoOrnamentSettings(),
        ) {
          val routeSource =
            rememberGeoJsonSource(id = "amtrak-routes", uri ="")

          val infiniteTransition = rememberInfiniteTransition()
          val animatedColor by
            infiniteTransition.animateColor(
              Color.hsl(0f, 1f, 0.5f),
              Color.hsl(0f, 1f, 0.5f),
              animationSpec =
                infiniteRepeatable(
                  animation =
                    keyframes {
                      durationMillis = 10000
                      for (i in 1..9) Color.hsl(i * 36f, 1f, 0.5f) at (i * 1000)
                    }
                ),
            )

          Anchor.Below("waterway_line_label") {
            LineLayer(
              id = "amtrak-routes",
              source = routeSource,
              color = const(animatedColor),
              cap = const(LineCap.Round),
              join = const(LineJoin.Round),
              width =
                interpolate(
                  type = exponential(1.2f),
                  input = zoom(),
                  7 to const(1.75.dp),
                  20 to const(22.dp),
                ),
            )
          }
        }
        DemoMapControls(cameraState, styleState)
      }
    }
  }
}
