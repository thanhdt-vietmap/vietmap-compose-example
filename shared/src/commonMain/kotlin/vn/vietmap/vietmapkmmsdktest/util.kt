package vn.vietmap.vietmapkmmsdktest

import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.fastRoundToInt
import io.github.dellisd.spatialk.geojson.Position
import org.jetbrains.compose.resources.ExperimentalResourceApi
import kotlin.math.pow
import kotlin.math.roundToInt

interface Demo {
    val name: String
    val description: String

    @Composable fun Component(navigateUp: () -> Unit)
}

data class StyleInfo(val name: String, val uri: String, val isDark: Boolean)

@OptIn(ExperimentalResourceApi::class)
val ALL_STYLES =
    listOf(
        StyleInfo("Bright", "https://maps.vietmap.vn/api/maps/light/styles.json?apikey=YOUR_API_KEY_HERE", false),
        StyleInfo("Liberty", "https://maps.vietmap.vn/api/maps/light/styles.json?apikey=YOUR_API_KEY_HERE", false),
        StyleInfo("Positron", "https://maps.vietmap.vn/api/maps/light/styles.json?apikey=YOUR_API_KEY_HERE", false),
        StyleInfo("Fiord", "https://maps.vietmap.vn/api/maps/light/styles.json?apikey=YOUR_API_KEY_HERE", true),
        StyleInfo("Dark", "https://maps.vietmap.vn/api/maps/light/styles.json?apikey=YOUR_API_KEY_HERE", true),

    )

val DEFAULT_STYLE = ALL_STYLES[0].uri

/** Caution: this converter results in a loss of precision far from the origin. */
class PositionVectorConverter(private val origin: Position) :
    TwoWayConverter<Position, AnimationVector2D> {
    override val convertFromVector: (AnimationVector2D) -> Position = { vector ->
        Position(
            longitude = vector.v1.toDouble() + origin.longitude,
            latitude = vector.v2.toDouble() + origin.latitude,
        )
    }

    override val convertToVector: (Position) -> AnimationVector2D = { pos ->
        val dLon = pos.longitude - origin.longitude
        val dLat = pos.latitude - origin.latitude
        AnimationVector2D(dLon.toFloat(), dLat.toFloat())
    }
}

internal fun Double.format(decimals: Int): String {
    val factor = 10.0.pow(decimals)
    return ((this * factor).fastRoundToInt() / factor).toString()
}

internal class FrameRateState(private val spinner: String = "◐◓◑◒") {
    private var rollingAverage by mutableStateOf(0.0)
    private var spinnerIndex by mutableStateOf(0)

    fun recordFps(framesPerSecond: Double) {
        rollingAverage = (rollingAverage * 0.9) + (framesPerSecond * 0.1)
        spinnerIndex = (spinnerIndex + 1) % spinner.length
    }

    val spinChar: Char
        get() = spinner[spinnerIndex]

    val avgFps: Int
        get() = rollingAverage.roundToInt()
}

@Composable expect fun getDefaultColorScheme(isDark: Boolean = false): ColorScheme

expect object Platform {
    val isAndroid: Boolean
    val isIos: Boolean
    val isDesktop: Boolean
    val isWeb: Boolean
}

val Platform.supportsLayers: Boolean
    get() = isAndroid || isIos

val Platform.supportsBlending: Boolean
    get() = isAndroid || isIos

val Platform.usesVietMapNative: Boolean
    get() = isAndroid || isIos
