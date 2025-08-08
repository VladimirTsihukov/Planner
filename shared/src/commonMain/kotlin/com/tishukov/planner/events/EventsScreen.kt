package com.tishukov.planner.events

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BoxScope.EventsScreen() {
    Text(
        text = "Events Screen",
        modifier = Modifier.align(Alignment.Center),
    )
}
