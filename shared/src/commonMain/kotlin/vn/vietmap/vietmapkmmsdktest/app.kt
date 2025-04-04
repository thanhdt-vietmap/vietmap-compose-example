package vn.vietmap.vietmapkmmsdktest

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vn.vietmap.vietmapcompose.compose.CameraState
import vn.vietmap.vietmapcompose.compose.StyleState
import vn.vietmap.vietmapcompose.core.OrnamentSettings
import vn.vietmap.vietmapkmmsdktest.demos.AnimatedLayerDemo
import vn.vietmap.vietmapkmmsdktest.demos.CameraFollowDemo
import vn.vietmap.vietmapkmmsdktest.demos.CameraStateDemo
import vn.vietmap.vietmapkmmsdktest.demos.ClusteredPointsDemo
import vn.vietmap.vietmapkmmsdktest.demos.EdgeToEdgeDemo
import vn.vietmap.vietmapkmmsdktest.demos.FrameRateDemo
import vn.vietmap.vietmapkmmsdktest.demos.MarkersDemo
import vn.vietmap.vietmapkmmsdktest.demos.StyleSwitcherDemo
import vn.vietmap.vietmapcompose.material3.controls.AttributionButton
import vn.vietmap.vietmapcompose.material3.controls.DisappearingCompassButton
import vn.vietmap.vietmapcompose.material3.controls.DisappearingScaleBar


private val DEMOS = buildList {
    add(StyleSwitcherDemo)
    if (Platform.supportsBlending) add(EdgeToEdgeDemo)
    if (Platform.supportsLayers) {
        add(MarkersDemo)
        add(ClusteredPointsDemo)
        add(AnimatedLayerDemo)
    }
    if (!Platform.isDesktop) add(CameraStateDemo)
    if (Platform.usesVietMapNative) add(CameraFollowDemo)
    if (!Platform.isDesktop) add(FrameRateDemo)
}

@Composable
fun DemoApp(navController: NavHostController = rememberNavController()) {
    MaterialTheme(colorScheme = getDefaultColorScheme()) {
        NavHost(
            navController = navController,
            startDestination = "start",
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End)
            },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) },
        ) {
            composable("start") { DemoMenu { demo -> navController.navigate(demo.name) } }
            DEMOS.forEach { demo ->
                composable(demo.name) { demo.Component { navController.popBackStack() } }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DemoMenu(navigate: (demo: Demo) -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("VietMap Compose Demos") }) }) { padding ->
        Column(
            modifier =
            Modifier.consumeWindowInsets(padding).padding(padding).verticalScroll(
                rememberScrollState()
            )
        ) {
            DEMOS.forEach { demo ->
                Column {
                    ListItem(
                        modifier = Modifier.clickable(onClick = { navigate(demo) }),
                        headlineContent = { Text(text = demo.name) },
                        supportingContent = { Text(text = demo.description) },
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoAppBar(demo: Demo, navigateUp: () -> Unit, alpha: Float = 1f) {
    var showInfo by remember { mutableStateOf(false) }

    TopAppBar(
        colors =
        TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = alpha)
        ),
        title = { Text(demo.name) },
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            if (Platform.supportsBlending) {
                IconButton(onClick = { showInfo = true }) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
                }
            }
        },
    )

    if (showInfo) {
        AlertDialog(
            onDismissRequest = { showInfo = false },
            title = { Text(text = demo.name) },
            text = { Text(text = demo.description) },
            confirmButton = { TextButton(onClick = { showInfo = false }) { Text("OK") } },
        )
    }
}

@Composable
fun DemoScaffold(demo: Demo, navigateUp: () -> Unit, content: @Composable () -> Unit) {
    Scaffold(topBar = { DemoAppBar(demo, navigateUp) }) { padding ->
        Box(modifier = Modifier.consumeWindowInsets(padding).padding(padding)) { content() }
    }
}

@Composable
fun DemoMapControls(
    cameraState: CameraState,
    styleState: StyleState,
    modifier: Modifier = Modifier,
    onCompassClick: () -> Unit = {},
) {
    if (Platform.supportsBlending) {
        Box(modifier = modifier.fillMaxSize().padding(8.dp)) {
            DisappearingScaleBar(
                metersPerDp = cameraState.metersPerDpAtTarget,
                zoom = cameraState.position.zoom,
                modifier = Modifier.align(Alignment.TopStart),
            )
            DisappearingCompassButton(
                cameraState,
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = onCompassClick,
            )
            AttributionButton(styleState, modifier = Modifier.align(Alignment.BottomEnd))
        }
    }
}

fun DemoOrnamentSettings(padding: PaddingValues = PaddingValues(0.dp)) =
    if (Platform.supportsBlending)
        OrnamentSettings.AllDisabled.copy(
            padding = padding,
            isLogoEnabled = true,
            logoAlignment = Alignment.BottomStart,
        )
    else OrnamentSettings.AllEnabled
