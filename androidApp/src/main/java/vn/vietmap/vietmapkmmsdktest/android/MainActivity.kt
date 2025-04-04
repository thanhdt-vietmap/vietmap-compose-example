package vn.vietmap.vietmapkmmsdktest.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import vn.vietmap.vietmapkmmsdktest.DemoApp
import vn.vietmap.vietmapkmmsdktest.Greeting
import vn.vietmap.vietmapkmmsdktest.VietMapCommon

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
//                DemoApp()
            GreetingView(modifier = Modifier.fillMaxSize())
            }

        }
    }
}

@Composable
fun GreetingView(modifier: Modifier) {
    VietMapCommon()
}


@Composable
fun VietMapView(){
    Text(text = "Hello Android")
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
//        GreetingView("Hello, Android!")
    }
}
