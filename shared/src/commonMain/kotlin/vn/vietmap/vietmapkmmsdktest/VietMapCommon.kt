package vn.vietmap.vietmapkmmsdktest

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.FeatureCollection
import io.github.dellisd.spatialk.geojson.Point
import io.github.dellisd.spatialk.geojson.Position
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import vietmapkmmsdktest.shared.generated.resources.Res
import vietmapkmmsdktest.shared.generated.resources.marker
import vn.vietmap.vietmapcompose.compose.ClickResult
import vn.vietmap.vietmapcompose.compose.VietMapGLCompose
import vn.vietmap.vietmapcompose.compose.layer.CircleLayer
import vn.vietmap.vietmapcompose.compose.layer.FillLayer
import vn.vietmap.vietmapcompose.compose.layer.LineLayer
import vn.vietmap.vietmapcompose.compose.layer.SymbolLayer
import vn.vietmap.vietmapcompose.compose.rememberCameraState
import vn.vietmap.vietmapcompose.compose.source.rememberGeoJsonSource
import vn.vietmap.vietmapcompose.core.CameraPosition
import vn.vietmap.vietmapcompose.core.OrnamentSettings
import vn.vietmap.vietmapcompose.core.source.GeoJsonOptions
import vn.vietmap.vietmapcompose.expressions.dsl.asNumber
import vn.vietmap.vietmapcompose.expressions.dsl.asString
import vn.vietmap.vietmapcompose.expressions.dsl.const
import vn.vietmap.vietmapcompose.expressions.dsl.feature
import vn.vietmap.vietmapcompose.expressions.dsl.image
import vn.vietmap.vietmapcompose.expressions.dsl.not
import vn.vietmap.vietmapcompose.expressions.dsl.offset
import vn.vietmap.vietmapcompose.expressions.dsl.step

private const val ROUTES_FILE = "files/data/amtrak_routes.geojson"
private const val LIME_SEATTLE_FILE = "files/data/lime_seattle.gbfs.json"
private val LIME_GREEN = Color(50, 205, 5)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun VietMapCommon() {
    val camera =
        rememberCameraState(
            firstPosition =
            CameraPosition(
                target = Position(
                    latitude = 39.534546289440264,
                    longitude = -97.41580394417868
                ), zoom = 2.0
            )
        )

    val coroutineScope = rememberCoroutineScope()
    VietMapGLCompose(
        cameraState = camera,
        onMapClick = { position, dpOffset ->
            val features = camera.queryRenderedFeatures(dpOffset)
            if (features.isNotEmpty()) {
                println("Clicked on ${features[0].json()}")
                ClickResult.Consume
            } else {
                ClickResult.Pass
            }
        },
        ornamentSettings = OrnamentSettings(
            isAttributionEnabled = false
        ),
        styleUri = "https://maps.vietmap.vn/api/maps/light/styles.json?apikey=YOUR_API_KEY_HERE"
    ) {

        val routeSource =
            rememberGeoJsonSource(id = "amtrak-routes", uri = Res.getUri(ROUTES_FILE))

        LineLayer(
            id = "example2",
            source = routeSource,
            sourceLayer = "waterway",
            color = const(Color.Red),
            width = const(2.dp)
        )

        val gbfsData by rememberGbfsFeatureState(LIME_SEATTLE_FILE)

        val bikeSource =
            rememberGeoJsonSource(
                "bikes",
                gbfsData,
                GeoJsonOptions(cluster = true, clusterRadius = 32, clusterMaxZoom = 16),
            )

        CircleLayer(
            id = "clustered-bikes",
            source = bikeSource,
            filter = feature.has("point_count"),
            color = const(LIME_GREEN),
            opacity = const(0.5f),
            radius =
            step(
                input = feature.get("point_count").asNumber(),
                fallback = const(15.dp),
                25 to const(20.dp),
                100 to const(30.dp),
                500 to const(40.dp),
                1000 to const(50.dp),
                5000 to const(60.dp),
            ),
            onClick = { features ->
                features.firstOrNull()?.geometry?.let {
                    coroutineScope.launch {
                        camera.animateTo(
                            camera.position.copy(
                                target = (it as Point).coordinates,
                                zoom = (camera.position.zoom + 2).coerceAtMost(20.0),
                            )
                        )
                    }
                    ClickResult.Consume
                } ?: ClickResult.Pass
            },
        )

        SymbolLayer(
            id = "clustered-bikes-count",
            source = bikeSource,
            filter = feature.has("point_count"),
            textField = feature.get("point_count_abbreviated").asString(),
            textFont = const(listOf("Noto Sans Regular")),
            textColor = const(MaterialTheme.colorScheme.onBackground),
        )

        CircleLayer(
            id = "unclustered-bikes-shadow",
            source = bikeSource,
            filter = !feature.has("point_count"),
            radius = const(13.dp),
            color = const(Color.Black),
            blur = const(1f),
            translate = offset(0.dp, 1.dp),
        )

        CircleLayer(
            id = "unclustered-bikes",
            source = bikeSource,
            filter = !feature.has("point_count"),
            color = const(LIME_GREEN),
            radius = const(7.dp),
            strokeWidth = const(3.dp),
            strokeColor = const(Color.White),
        )
        val iconSource = rememberGeoJsonSource(
            id = "marker-icon",
            data = Point( coordinates = Position(-97.41580394417868, 39.534546289440264))
        )
        val marker = painterResource(Res.drawable.marker)

        FillLayer(
            id = "marker-fill",
            source = routeSource,
            color = const(Color.Blue),
            opacity = const(0.5f),

            filter = !feature.has("point_count"),
        )

        SymbolLayer(
            id = "simple-marker",
            source = iconSource,
            iconImage = image(marker),
            iconSize = const(1.5f),
            iconColor = const(Color.White),
            iconOffset = offset(0.dp, -2.dp),
        )
    }
}


@Composable
private fun rememberGbfsFeatureState(gbfsFilePath: String): State<FeatureCollection> {
    val dataState = remember { mutableStateOf(FeatureCollection()) }
    LaunchedEffect(gbfsFilePath) {
        val response = readGbfsData(gbfsFilePath)
        dataState.value = response
    }
    return dataState
}

@OptIn(ExperimentalResourceApi::class)
private suspend fun readGbfsData(gbfsFilePath: String): FeatureCollection {
    val bodyString = Res.readBytes(gbfsFilePath).decodeToString()
    val body = Json.parseToJsonElement(bodyString).jsonObject
    val bikes = body["data"]!!.jsonObject["bikes"]!!.jsonArray.map { it.jsonObject }
    val features =
        bikes.map { bike ->
            Feature(
                id = bike["bike_id"]!!.jsonPrimitive.content,
                geometry =
                Point(
                    Position(
                        longitude = bike["lon"]!!.jsonPrimitive.double,
                        latitude = bike["lat"]!!.jsonPrimitive.double,
                    )
                ),
                properties =
                mapOf(
                    "vehicle_type" to (bike["vehicle_type"] ?: JsonNull),
                    "vehicle_type_id" to (bike["vehicle_type_id"] ?: JsonNull),
                    "last_reported" to (bike["last_reported"] ?: JsonNull),
                    "vehicle_range_meters" to (bike["vehicle_range_meters"] ?: JsonNull),
                    "is_reserved" to (bike["is_reserved"] ?: JsonNull),
                    "is_disabled" to (bike["is_disabled"] ?: JsonNull),
                ),
            )
        }
    return FeatureCollection(features)
}
