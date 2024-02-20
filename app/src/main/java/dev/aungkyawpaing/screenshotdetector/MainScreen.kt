package dev.aungkyawpaing.screenshotdetector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
  counter: Int
) {
  Surface {
    Box(
      modifier = Modifier
        .fillMaxSize()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .align(Alignment.Center),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Text(
          "Number of screenshot taken",
          style = MaterialTheme.typography.titleLarge,
          textAlign = TextAlign.Center,
          modifier = Modifier
            .fillMaxWidth()
        )
        Text(
          "$counter",
          style = MaterialTheme.typography.titleLarge,
          textAlign = TextAlign.Center,
          modifier = Modifier
            .fillMaxWidth()
        )
      }

    }
  }
}


@Preview
@Composable
fun MainScreenPreview() {
  MainScreen(5)
}