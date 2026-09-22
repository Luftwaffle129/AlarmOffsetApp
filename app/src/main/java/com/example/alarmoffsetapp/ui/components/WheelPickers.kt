package com.example.alarmoffsetapp.ui.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlin.math.abs

@Composable
fun TimeWheelPicker(
    maxValue: Int,
    listState: LazyListState,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isTwoDigits: Boolean = true,
    is12HourFormat: Boolean = false,
) {
    val baseFontSize = 48f // base font size before scaling for middle, needs to not exceed box dimensions

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
                onSelected(selected)
            }
    }

    LazyColumn(
        modifier = modifier
            .height(270.dp)
            .wrapContentWidth(),
        state = listState,
        flingBehavior = flingBehavior,
        contentPadding = PaddingValues(vertical = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val layoutInfo = listState.layoutInfo

        items(Int.MAX_VALUE) { value ->

            val itemInfo = layoutInfo.visibleItemsInfo // get item information
                .firstOrNull { it.index == value }

            val center =    // get center of the lazy column
                (layoutInfo.viewportStartOffset +
                        layoutInfo.viewportEndOffset) / 2f

            val distance = itemInfo?.let { // find the distance of the item from the center
                abs(
                    (it.offset + it.size / 2f) - center
                )
            } ?: Float.MAX_VALUE

            val centerMultiplier = (1f - distance / 100f) // calculate the multiplier from the distance
                .coerceIn(0f, 1f)

            val fontSize = baseFontSize + baseFontSize * centerMultiplier * 0.5f
            val opacity = 0.5f + centerMultiplier * 0.5f

            // handle if wheel is for hours in the 12hour format
            val displayValue = if (is12HourFormat) {
                val temp = value % 12
                if (temp == 0) 12 else temp
            } else
                value % maxValue

            Box(
                modifier = Modifier
                    .height(90.dp)
                    .width(90.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isTwoDigits) "%02d".format(displayValue) else (displayValue).toString(),
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = fontSize.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = opacity)
                )
            }
        }
    }
}

@Composable
fun DayPeriodWheelPicker(
    listState: LazyListState,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val timePeriods = listOf("am", "pm")

    val baseFontSize = 32f // base font size before scaling for middle, needs to not exceed box dimensions

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
                onSelected(selected)
            }
    }

    LazyColumn(
        modifier = modifier
            .height(270.dp)
            .wrapContentWidth(),
        state = listState,
        flingBehavior = flingBehavior,
        contentPadding = PaddingValues(vertical = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val layoutInfo = listState.layoutInfo

        items(timePeriods) { value ->

            val itemInfo = layoutInfo.visibleItemsInfo // get item information
                .firstOrNull { it.index == timePeriods.indexOf(value) }

            val center =    // get center of the lazy column
                (layoutInfo.viewportStartOffset +
                        layoutInfo.viewportEndOffset) / 2f

            val distance = itemInfo?.let { // find the distance of the item from the center
                abs(
                    (it.offset + it.size / 2f) - center
                )
            } ?: Float.MAX_VALUE

            val centerMultiplier = (1f - distance / 100f) // calculate the multiplier from the distance
                .coerceIn(0f, 1f)

            val fontSize = baseFontSize + baseFontSize * centerMultiplier * 0.5f
            val opacity = 0.5f + centerMultiplier * 0.5f

            Box(
                modifier = Modifier
                    .height(90.dp)
                    .width(90.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = fontSize.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = opacity)
                )
            }
        }
    }
}