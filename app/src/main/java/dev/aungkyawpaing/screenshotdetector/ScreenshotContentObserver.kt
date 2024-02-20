package dev.aungkyawpaing.screenshotdetector

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore

class ScreenshotContentObserver(
  handler: Handler,
  private val context: Context,
  private val onScreenshotTaken: () -> Unit,
) : ContentObserver(handler) {

  private var previousPath: String? = null

  override fun onChange(selfChange: Boolean, uri: Uri?) {
    //Query filepath
    context.contentResolver.query(
      uri!!,
      arrayOf(MediaStore.Images.Media.DATA),
      null,
      null,
      null
    )?.use { cursor ->
      if (cursor.moveToLast()) {
        // Get absolute file path
        val dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
        val path = cursor.getString(dataColumnIndex).lowercase()
        // If path contains screen, it's probably a screenshot
        val isScreenshot = path.contains("screenshot")
        // Some OS create a temp file first before writing the image
        // We don't want to take these temp file into account
        val notPendingFile = !path.contains(".pending")

        if (isScreenshot && notPendingFile && previousPath != path) {
          onScreenshotTaken()
        }
        previousPath = path
      }
    }
    super.onChange(selfChange, uri)
  }
}