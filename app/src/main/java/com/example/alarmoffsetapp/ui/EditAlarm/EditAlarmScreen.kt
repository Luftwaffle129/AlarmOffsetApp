package com.example.alarmoffsetapp.ui.EditAlarm

import android.R.attr.alpha
import android.R.attr.scaleX
import android.R.attr.scaleY
import android.annotation.SuppressLint
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import com.example.alarmoffsetapp.ui.theme.AppTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import java.util.Locale
import kotlin.math.abs

@Composable
fun EditAlarmScreen(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {

    }
}

@Composable
fun EditAlarmBody(
    modifier: Modifier
) {

}

@Composable
fun UnitTimePicker(
    startValue: Int,
    maxValue: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val middleIndex = Int.MAX_VALUE / 2 // get middle index for initialization
    var currentIndex by remember { mutableIntStateOf(middleIndex) }

    val baseFontSize = 48f // base font size before scaling for middle, needs to not exceed box dimensions

    // remember list state between recompositions
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = middleIndex - (middleIndex % maxValue) + startValue
    )

    val flingBehavior = rememberSnapFlingBehavior(listState)

    // Flow of middle value
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo
                .minByOrNull { item ->
                    abs(
                        item.offset +
                                item.size / 2 -
                                listState.layoutInfo.viewportEndOffset / 2
                    )
                }
                ?.index
        }
            .filterNotNull()
            .distinctUntilChanged()
            .collect { selected ->
                currentIndex = selected
                onSelected(selected)
            }
    }

    LazyColumn(
        modifier = modifier.height(270.dp),
        state = listState,
        flingBehavior = flingBehavior,
        contentPadding = PaddingValues(vertical = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val layoutInfo = listState.layoutInfo

        items(Int.MAX_VALUE) { value ->
            val itemInfo = layoutInfo.visibleItemsInfo
                .firstOrNull { it.index == value }

            val center =
                (layoutInfo.viewportStartOffset +
                        layoutInfo.viewportEndOffset) / 2f

            val distance = itemInfo?.let {
                abs(
                    (it.offset + it.size / 2f) - center
                )
            } ?: Float.MAX_VALUE

            val progress = (1f - distance / 100f)
                .coerceIn(0f, 1f)

            val fontSize = baseFontSize + baseFontSize * progress * 0.5f
            Box(
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "%02d".format(value % maxValue),
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = fontSize.sp
                )
            }
        }
    }
}

@SuppressLint("DefaultLocale") // Locale is not used as wheel picker is for the minutes in hh:mm
@Preview(showBackground = true)
@Composable
fun WheelPicker2Preview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box {
                UnitTimePicker(
                    startValue = 0,
                    maxValue = 60,
                    onSelected = {},
                    modifier = Modifier.padding(top = 64.dp)
                )
            }
        }
    }
}