package dev.aungkyawpaing.screenshotdetector

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableIntStateOf
import dev.aungkyawpaing.screenshotdetector.ui.theme.ScreenshotDetectorTheme

class MainActivity : ComponentActivity() {

  private var counter = mutableIntStateOf(0)

  private val screenCaptureCallback = ScreenCaptureCallback {
    counter.intValue += 1
  }

  private val screenshotContentObserver by lazy {
    val handlerThread = HandlerThread("screenshot_change_observer")
    val handler = object : Handler(handlerThread.looper) {
      // DO NOTHING
    }

    ScreenshotContentObserver(handler, applicationContext, onScreenshotTaken = {
      counter.intValue += 1
    })
  }

  private val isOSVersionEqualOrAboveAndroid14 =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      ScreenshotDetectorTheme {
        MainScreen(counter.intValue)
      }
    }
  }

  override fun onResume() {
    super.onResume()
    if (isOSVersionEqualOrAboveAndroid14) {
      registerScreenCaptureCallback(mainExecutor, screenCaptureCallback)
    } else {
      contentResolver.registerContentObserver(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        true,
        screenshotContentObserver
      )
    }
  }

  override fun onPause() {
    super.onPause()
    if (isOSVersionEqualOrAboveAndroid14) {
      unregisterScreenCaptureCallback(screenCaptureCallback)
    } else {
      contentResolver.unregisterContentObserver(screenshotContentObserver)
    }
  }
}