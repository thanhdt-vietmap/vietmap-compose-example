package vn.vietmap.vietmapkmmsdktest

@kotlinx.serialization.Serializable
data class GeoJsonData(
    val type: String,
    val crs: Crs,
    val features: List<Feature>
)

@kotlinx.serialization.Serializable
data class Crs(val type: String, val properties: CrsProperties)
@kotlinx.serialization.Serializable
data class CrsProperties(val name: String)

@kotlinx.serialization.Serializable
data class Feature(
    val type: String,
    val id: Int,
    val geometry: Geometry,
    val properties: Properties
)

@kotlinx.serialization.Serializable
data class Geometry(
    val type: String,
    val coordinates: List<List<List<Double>>>
)

@kotlinx.serialization.Serializable
data class Properties(
    val OBJECTID: Int,
    val name: String,
    val shape_leng: Double
)
